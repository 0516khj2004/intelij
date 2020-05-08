package com.example.demo.dao;

import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class UserDaoService implements IUserService {
    private static List<User> users = new ArrayList<>();
    private static  int userCount =3;
    private User user = new User();

    static {
        users.add(new User(1,"kenneth", new Date(), "test1", "701010-111111"));
        users.add(new User(2,"Alice", new Date(), "test2", "701010-222222"));
        users.add(new User(3,"Elena", new Date(), "test3", "701010-333333"));
    }

    public List<User> getUserList()
    {
        return  users;
    }

    public User getUser(Integer id) {

        for (User user: users) {
            if(id.equals(user.getId())){
                return user;
            }
        }
        return null;
    }

    public  User save(User newUser){
        if(newUser.getId() == null){
            newUser.setId(users.get(users.size() - 1).getId() + 1); //id  1, 3 -> 4추가
        }
        users.add(newUser);
        return newUser;
    }

   public  User delete(Integer id){
       Iterator<User> iterator = users.iterator(); //iterator() list에 들어있는 값을 순차적으로 가져옴
        //list -> ordering
        //map -> set ordering

       while (iterator.hasNext()){
           User user = iterator.next();

           if(user.getId() == id){
               iterator.remove();
               return user;
           }
       }
       return null;
   }

   public User update(User modifyUser){
       Iterator<User> iterator = users.iterator(); //iterator() list에 들어있는 값을 순차적으로 가져옴
       //list -> ordering
       //map -> set ordering

       while (iterator.hasNext()){
           User user = iterator.next();

           if (user.getId() == modifyUser.getId()) {
               user.setName(modifyUser.getName());
               user.setLocalDate(modifyUser.getLocalDate());
               user.setPassword(modifyUser.getPassword());
               user.setSsn(modifyUser.getSsn());

               return user;
           }
       }
       return null;
   }

}
