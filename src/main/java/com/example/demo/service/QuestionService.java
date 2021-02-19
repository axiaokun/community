package com.example.demo.service;

import com.example.demo.dto.PaginationDto;
import com.example.demo.dto.QuestionDto;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.exception.CustomizeException;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDto list(Integer page, Integer size) {
        PaginationDto paginationDto = new PaginationDto();
        Integer count = questionMapper.count();
        paginationDto.setPagination(count, page, size);
        if (page < 1){
            page = 1;
        }
        if (page > paginationDto.getTotalPage()){
            page = paginationDto.getTotalPage();
        }

        Integer offset = size*(page - 1);
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDto> questionDtoList = new ArrayList<>();

        for (Question question : questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        paginationDto.setQuestions(questionDtoList);

        return paginationDto;
    }

    public PaginationDto listByUserId(String accountId, Integer page, Integer size) {
        PaginationDto paginationDto = new PaginationDto();
        Integer count = questionMapper.countByUserId(accountId);
        paginationDto.setPagination(count, page, size);
        if (page < 1){
            page = 1;
        }
        if (page > paginationDto.getTotalPage()){
            page = paginationDto.getTotalPage();
        }

        Integer offset = size*(page - 1);
        List<Question> questions = questionMapper.listByUserId(accountId,offset,size);
        List<QuestionDto> questionDtoList = new ArrayList<>();

        for (Question question : questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        paginationDto.setQuestions(questionDtoList);

        return paginationDto;
    }

    public QuestionDto getById(Integer id) {
        Question question = questionMapper.getById(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question, questionDto);
        User user = userMapper.findById(question.getCreator());
        questionDto.setUser(user);
        return questionDto;
    }

    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            // 创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else {
            // 更新
            question.setGmtModified(question.getGmtCreate());
            questionMapper.update(question);

        }
    }
}
