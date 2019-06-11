package com.song.songup.shoppingapp.mvp.ui.activity;

import java.util.List;

/**
 * Created by ASUS on 2017/11/3.
 */

public class GetRoomResponse {


    /**
     * status : 1
     * msg : 操作完成
     * data : {"admin":"dl_gudong1_1","username":"465ae9","room_id":"23","third_room_id":5887556}
     */

    private int status;
    private String msg;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * admin : dl_gudong1_1
         * username : 465ae9
         * room_id : 23
         * third_room_id : 5887556
         */

        private String admin;
        private String username;
        private String room_id;
        private String third_room_id;
        private int chatroom_role;
        private String room_name;
        private List<String> fobid_words;
        private int user_count;

        private int allow_login=1;

        public int getAllow_login() {
            return allow_login;
        }

        public void setAllow_login(int allow_login) {
            this.allow_login = allow_login;
        }


        public int getChatroom_role() {
            return chatroom_role;
        }

        public void setChatroom_role(int chatroom_role) {
            this.chatroom_role = chatroom_role;
        }

        public String getAdmin() {
            return admin;
        }

        public void setAdmin(String admin) {
            this.admin = admin;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getThird_room_id() {
            return third_room_id;
        }

        public void setThird_room_id(String third_room_id) {
            this.third_room_id = third_room_id;
        }

        public List<String> getFobid_words() {
            return fobid_words;
        }

        public void setFobid_words(List<String> fobid_words) {
            this.fobid_words = fobid_words;
        }

        public String getRoom_name() {
            return room_name;
        }

        public void setRoom_name(String room_name) {
            this.room_name = room_name;
        }

        public int getUser_count() {
            return user_count;
        }

        public void setUser_count(int user_count) {
            this.user_count = user_count;
        }
    }
}
