package com.ticket.user.services;

import com.mongodb.client.MongoClients;
import com.ticket.user.DTOs.UserDTO;
import com.ticket.user.entities.PagedResponse;
import com.ticket.user.entities.User;
import com.ticket.user.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public PagedResponse<UserDTO> userFilterHandler(
        String userId,
        String phoneNumber,
        String residentName,
        String residentId,
        int page,
        int size

    )
    {
        List<User> users = new ArrayList<User>();
        MongoTemplate mongoTemplate = new MongoTemplate(MongoClients.create(
            "mongodb+srv://huynhtrongnghia10012000:18120478@ryucluster.hkwulwu.mongodb.net/?retryWrites=true&w=majority&appName=RyuCluster"), 
            "MDM");
        Query query = new Query();
        if (!userId.isBlank()&&userId!=null)
        {
            ObjectId userObjectId = new ObjectId(userId);
            query.addCriteria(Criteria.where("userId").is(userObjectId));
        }

        if (!phoneNumber.isBlank() && phoneNumber!=null)
        {
            query.addCriteria(Criteria.where("phoneNumber").is(phoneNumber));
        }

        if (!residentName.isBlank() && residentName!= null)
        {
            query.addCriteria(Criteria.where("residentName").regex(".*"+residentName+".*"));
        }

        if (!residentId.isBlank() && residentId!= null)
        {
            query.addCriteria(Criteria.where("residentId").is(residentId));
        }

        long totalItems = mongoTemplate.count(query, User.class);

        Pageable pageable = PageRequest.of(page, size);
        query.with(pageable);

        logger.info("Mongo Query: {}", query);
        users = mongoTemplate.find(query, User.class);
        logger.info("Events found: {}", users.size());

        List<UserDTO> userDTOs = new DTOHandler().convertUserToDTO(users);

        int totalPages = (int) Math.ceil((double) totalItems / size);

        return new PagedResponse<>(totalItems, totalPages, page, userDTOs);

    }

//    public ResponseTemplateVO getUserWithDepartment(String id) {
//        User user = this.getById(new ObjectId(id));
//
//        Department department = restTemplate.getForObject("http://service01/service01/" + user.getDepartmentId(), Department.class);
//
//        return new ResponseTemplateVO(user, department);
//    }

    
}
