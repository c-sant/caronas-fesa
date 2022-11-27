package com.carona.controllers;

import com.carona.models.PostModel;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class CardPostController {
    PostModel post;
    
    public CardPostController() {
    }

    public CardPostController(PostModel post) {
        this.post = post;
    }

    @FXML
    Text txtTitulo;
    
    
    @FXML
    public void initialize() {
        txtTitulo.setText(post.getTitle());
    }
}
