package com.carona.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.carona.DAO.NotificationDAO;
import com.carona.DAO.PostDAO;
import com.carona.exceptions.BlankFieldsException;
import com.carona.exceptions.EntityAlreadyExistsException;
import com.carona.exceptions.EntityDoesNotExistException;
import com.carona.filters.PostFilter;
import com.carona.helpers.PostAndNotificationHelper;
import com.carona.models.PostModel;

public class PostService {
    PostDAO postDAO = new PostDAO();
    NotificationDAO notificationDAO = new NotificationDAO();
    PostAndNotificationHelper helper = new PostAndNotificationHelper();

    public void create(PostModel post) throws BlankFieldsException, EntityAlreadyExistsException, SQLException {
        Boolean postExists = postDAO.readById(post) != null;


        if (postExists) {
            throw new EntityAlreadyExistsException("Post já existe com id " + post.getId()); 
        }

        validatePost(post);
        postDAO.insert(post);

        notifySubscribers(post);
    }

    private void notifySubscribers(PostModel post) throws SQLException {
        notificationDAO.createMultipleNotifications(post);
    }

    public void update(PostModel post) throws SQLException, EntityDoesNotExistException, BlankFieldsException  {
        Boolean postDoesNotExist = postDAO.readById(post) == null;

        if (postDoesNotExist) {
            throw new EntityDoesNotExistException("Post não existe com id " + post.getId()); 
        }

        validatePost(post);
        postDAO.update(post);
    }

    public void remove(PostModel post) throws SQLException {
        postDAO.remove(post);
    }

    public PostModel readById(Integer id) throws SQLException {
        PostModel postFound = postDAO.readById(new PostModel(id));

        return postFound;
    }

    public List<PostModel> readAll() throws SQLException {
        List<PostModel> postsFound = postDAO.readAll();

        return postsFound;
    }

    private void validatePost(PostModel post) throws BlankFieldsException {
        if (post.getPlaceOfDeparture() == null) {
            throw new BlankFieldsException("Local de partida não especificado.");
        }

        if (post.getDestination() == null) {
            throw new BlankFieldsException("Local de destino não especificado.");
        }

        if (post.getAvailableWeekdays() == null) {
            throw new BlankFieldsException("Horários disponíveis não especificados");
        }
        
        if (post.getCreator() == null) {
            throw new BlankFieldsException("Criador do post não especificado.");
        }

        if (post.getTitle() == null) {
            throw new BlankFieldsException("Título do post não especificado.");
        }

        if (post.getAvailableSeats() == null) {
            throw new BlankFieldsException("Número de vagas do post não especificado.");
        }
    }

    public List<PostModel> readByAdvancedFilter(PostFilter postFilter) throws SQLException {
        return postDAO.readByAdvancedFilter(postFilter);
    }

    public String getLatAndLon(String parameter) throws MalformedURLException, IOException{
        return helper.requestGetLatAndLon(parameter);
    }

   

 
}

