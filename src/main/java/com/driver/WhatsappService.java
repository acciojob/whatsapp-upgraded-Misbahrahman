package com.driver;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class WhatsappService {
    //repo too

    HashMap<String , User> userHashMap;
    HashMap<String , Group> groupHashMap;
    HashMap<Integer , Message> messageHashMap;
    HashMap<String , List<User>> userGroupMap;
    HashMap<String , List<Message>> groupMessagesMap;
    HashMap<User , List<Integer>> msgUserMap;

    int grpId;

    int msgId;



    WhatsappService(){
        this.userHashMap = new HashMap<>();
        this.groupHashMap = new HashMap<>();
        this.messageHashMap = new HashMap<>();
        this.userHashMap = new HashMap<>();
        this.groupMessagesMap = new HashMap<>();
        this.msgUserMap = new HashMap<>();
        this.grpId = 1;
        this.msgId = 1;
    }


    public String createUser(String name, String mobile) {
        if(userHashMap.containsKey(mobile))return "User already exists";
        userHashMap.put(mobile , new User(name , mobile));
        return "SUCCESS";
    }

    public Group createGroup(List<User> users) {
        Group group = null;
        if(users.size() == 2){
            String name = users.get(1).getName();
            group = new Group(name , 2);
            groupHashMap.put(name , group);
        }else if (users.size() > 2){
            grpId++;
            String name = "Group" + String.valueOf(grpId);
            group = new Group(name , users.size());
            groupHashMap.put(name , group);
        }

        userGroupMap.put(group.getName() , users);
        return group;
    }


    public int createMessage(String content) {
        int id = msgId++;
        Date date = new Date();
        messageHashMap.put(id , new Message(id , content , date));
        return id;
    }

    public int sendMessage(Message message, User sender, Group group) {
        String grpName = group.getName();

        if(!groupHashMap.containsKey(grpName))return -1;

        List<User> users = userGroupMap.get(grpName);
        if(!users.contains(sender))return -2;

        if(!msgUserMap.containsKey(sender))msgUserMap.put(sender , new ArrayList<>());

        msgUserMap.get(sender).add(message.getId());

        if(!groupMessagesMap.containsKey(grpName))groupMessagesMap.put(grpName , new ArrayList<>());

        groupMessagesMap.get(grpName).add(message);

        return groupMessagesMap.get(grpName).size();

    }


    public int changeAdmin(User approver, User user, Group group) {

        String grpName = group.getName();
        if(!groupHashMap.containsKey(grpName))return -1;

        if(userGroupMap.get(grpName).get(0) != approver)return -2;

        if(!userGroupMap.get(grpName).contains(user))return -3;

        User newAdmin = user;
        List<User> users = userGroupMap.get(grpName);
        users.add(0 , newAdmin);

        return 0;

    }

    public int removeUser(User user) {

        if(!userHashMap.containsKey(user))return -1;
        String grpName = "";
        List<User> users = null;
        for(String x : userGroupMap.keySet()){
            List<User> list = userGroupMap.get(x);
            if(list.contains(user)){
                users = list;
                grpName = x;
            }
        }
        if(users.get(0).equals(user))return -2;


        //del messages and user
        userHashMap.remove(user.getMobile());
        users.remove(user);
        List<Message> list = groupMessagesMap.get(grpName);
        for(int x : msgUserMap.get(user)){
            Message message = messageHashMap.get(x);
            messageHashMap.remove(message.getId());
            if(list.contains(message))list.remove(message);
        }
        msgUserMap.remove(user);



        return users.size() + list.size() + messageHashMap.size();


    }
}
