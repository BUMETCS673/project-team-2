package com.soloSavings.repository;

import com.soloSavings.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * Copyright (c) 2023 Team 2 - SoloSavings
 * Boston University MET CS 673 - Software Engineering
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Team 2 - SoloSavings Application
 */
@Repository
public interface CommentsRepository extends JpaRepository<Comments, Integer> {

    @Query("SELECT c FROM Comments c WHERE c.id = ?1 AND c.user_id = ?2")
    Comments findByCommentsId(Integer comments_id, Integer user_id);


    @Query("SELECT c FROM Comments c where c.user_id=?1")
    List<Comments> allList(Integer user_id);

}
