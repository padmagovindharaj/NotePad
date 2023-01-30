package com.notepad.NotepadHandsOn;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class UsersDAO {
    private static List<Users> users = new ArrayList<>();
    private static int userCount = 0;
    static {
        users.add(new Users(++userCount, "ragul", LocalDate.now().minusYears(23)));
        users.add(new Users(++userCount, "padmaPriya", LocalDate.now().minusYears(22)));
        users.add(new Users(++userCount, "PriyaDharsini", LocalDate.now().minusYears(23)));
    }

    public List<Users> findAll() {
        return users;
    }

    public Users findOne(int id) {
        for(int i = 0 ; i<users.size() ; i++)
        {
            if(users.get(i).getId() == id)
            {
                return users.get(i);
            }
        }
        return null;
    }

    public Users saveUser(Users user) {
        user.Id = ++userCount;
        users.add(user);
        return user;
    }
    
    public void deleteOne(int id) {
        for(int i = 0 ; i<users.size() ; i++)
        {
            if(users.get(i).getId() == id)
            {
                users.remove(i);
            }
        }
    }

}
