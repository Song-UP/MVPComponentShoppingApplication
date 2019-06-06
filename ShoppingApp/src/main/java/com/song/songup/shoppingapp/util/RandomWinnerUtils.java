package com.song.songup.shoppingapp.util;

import com.song.songup.shoppingapp.mvp.model.been.main.Winger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RandomWinnerUtils {
    // 形容词
    static String[] c1 = {"勇敢", "憔悴", "失落", "潇洒", "傲气", "沉敛", "禁戒", "大大", "小小", "牛掰", "高兴", "开心", "老", "新", "成功", "好", "诚实", "努力", "拼命", "无谓", "牛叉", "坏坏", "给力"};
    static String[] c2 = {"兄弟", "哥们", "人", "游侠", "工匠", "成功者", "巨人", "文人", "闲人", "伙伴", "刺客", "老师", "老实人", "老者", "王者", "帅哥", "美女", "大爷", "神", "魔王", "先生", "官人", "管家", "司机", "旅客", "赢家", "王爷", "王"};
    // 副词
    static String[] c3 = {"闷声", "偷偷", "重新", "继续", "第一次", "就是要", "偏要", "努力", "经常", "天天", "很想", "不相信不能", "想", "再来"};
    // 动词
    static String[] c4 = {"赚钱", "发财", "成功", "大赚", "收钱", "游荡", "游历", "旅游", "去玩", "有钱", "很有钱", "消费", "花钱", "谈恋爱", "交朋友"};

    // 专属词库
    static String[] c5 = {
            "青春一去沧桑笑",
            "沉敛一生",
            "成熟的身心疲惫",
            "御姐控的大叔酱",
            "注定沧桑",
            "成熟仔",
            "一丝沧桑",
            "胡渣熟男",
            "国民萌叔",
            "叼着棒棒糖闯天下",
            "很酷不撩妹",
            "傲气独走生",
            "手握风云",
            "野性与傲骨",
            "背叛゜全杀无赦",
            "′品味分裂者",
            "过分钟情",
            "我的未来我做主",
            "忘记过去，嫣然一笑。",
            "旧里迟暮",
            "青丝到白发",
            "当时年少春衫薄i",
            "浪迹天涯只为你",
            "有你、已滿足",
            "成长中的男神",
            "妳我即是江湖",
            "不怕留给时光遗忘",
            "归途的路",
            "♀我的爱已过期",
            "穷得只剩一身霸气",
            "天黑路滑人心杂",
            "笑对孤独",
            "带着梦想出发",
            "心随你而去",
            "—戴着耳机听世界",
            "淡然置之",
            "墨雨汐",
            "曾经沧海",
            "醉梦人生",
            "再見小時候",
            "往事风中埋",
            "尘烟酒瘾",
            "不如放纵",
            "负尽天下人",
            "倦了轻狂少年",
            "临风不自傲",
            "青衫落拓",
            "失落陪我牵手",
            "瀚水长流",
            "不悔的年代",
            "苟活于世",
            "平安喜乐",
            "岁月无忧",
            "玩物丧志",
            "怪咖先生",
            "爷、就一校草",
            "娘、就一校花",
            "青怏",
            "步步皆殇っ",
            "冷夜·残月",
            "洒脱",
            "零度 幻影",
            "独斟烈酒",
            "孤王戏",
            "弑念ン凌龙",
            "疾影＠天龙",
            "痞性",
            "剩余",
            "春花烂漫·",
            "*当空皓月/",
            "My啖莣⒈苆",
            "≠、臭包子",
            "情感荒废者",
            "死性不改",
            "素笺ベ",
            "旧街浪人",
            "初醒",
            "天空微晴",
            "时光滥好人",
            "[刺青]",
            "烟酒为伴",
            "过分喜欢",
            "我的爱已过期",
            "ヅ失溫℡"};

    // 火星文
    static String[] c6 = {"ヅ", "≠", "ベ", "っ", "ン", "＠", "°", "あ", "ね", "し", "ま", "な", "!", "?"};
    static String[] phone = {"130", "131", "132", "133", "134", "135", "136", "137", "138", "139",  "150", "151", "152", "153", "154", "155", "156", "157", "158", "159", "171", "172", "173", "175", "176", "177", "178", "179", "180", "181", "182", "183", "184", "185", "186", "187", "188", "189"};
    static String[] zimu = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    static int[] shuzi = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    
    static String[]lottery ={
        "极速快3",
                "江苏快3",
                "安徽快3",
                "幸运快3",
                "一分快3",
                "传统江苏快3",
                "传统安徽快3",
                "传统河北快3",
                "传统广西快3",
                "传统甘肃快3",
                "传统湖北快3",
                "传统吉林快3",
                "传统江西快3",
                "极速时时彩",
                "天津时时彩",
                "重庆时时彩",
                "传统天津时时彩",
                "传统重庆时时彩",
                "广东11选5",
                "传统广东11选5",
                "极速快乐10分",
                "传统广东快乐10分",
                "传统广西快乐10分",
                "极速六合彩",
                "极速10分六合彩",
                "极速福彩3D",
                "极速排列三"
    };

    // 三种模式
    public static String getRandomName() {
        double modeRandom = Math.random();
        String name = "";
        if (modeRandom < 0.3) {
            name = getRandomCharacter();
        } else if (modeRandom < 0.5) {
            name = getRandomPhonenum();
        } else {
            name = getRandomAlphanum();
        }
        return name + "***";
    }

    public static String getRandomMoney() {
        DecimalFormat decimalFormat =new DecimalFormat("0.00");
        double randomMoney = Math.random();
        if (randomMoney < 0.6) {
            //   return (2 + 1000 * Math.random()).toFixed(2);
            return decimalFormat.format(2 + 1000 * Math.random());
        } else if (randomMoney < 0.9) {
            // return (50000 * Math.random()).toFixed(2);
            return decimalFormat.format(50000 * Math.random());
        } else {
            // return (50000 + 50000 * Math.random()).toFixed(2)
            return decimalFormat.format(50000 + 50000 * Math.random());
        }
    }

    public  static String getRandomCharacter() {
        // 两种模式，1-形容词+名字，2-副词+动词
        String[] ca = c3;
        String[] cb = c4;
        String cd = "地";
        if (Math.random() < 0.7) {
            ca = c1;
            cb = c2;
            cd = "的";
        }
        String str = "";

        // 组合或预制
        if (Math.random() < 0.3) {
            str += c5[getRandom(c5.length)];
        } else {
            str += ca[getRandom(ca.length)];
            if (Math.random() < 0.1) {
                str += c6[getRandom(c6.length)] + str;
            }
            else if (Math.random() < 0.2) {
                str += cd;
            }
            str += cb[getRandom(cb.length)];
        }
        // 前缀
        if (Math.random() < 0.1) {
            str = c6[getRandom(c6.length)] + str;
        }
        // 后缀
        if (Math.random() < 0.1) {
            str += c6[getRandom(c6.length)];
        }
        return str;
    }

    public static String getRandomPhonenum() {
        String randomPhone = phone[getRandom(phone.length)];
        for (int i = 0; i < 5; i++) {
            randomPhone += new String(String.valueOf(getRandom(10)));
        }
        return randomPhone;
    }

    public static String getRandomAlphanum() {
        String str = "";
        int len = 1 + getRandom(4);
        boolean isNum = false;
        for (int i = 0; i < len; i++) {
            if (Math.random() < 0.3 || isNum) {
                isNum = true;
                str += shuzi[getRandom(10)];
            } else {
                String a = zimu[getRandom(26)];
                if (i == 0) {
                    if (Math.random() < 0.25) {
                        a = a.toUpperCase();
                    }
                }
                str += a;
            }
        }
        return str;
    }

    public static int getRandom(int len) {
        return (int) (len * Math.random());
    }

    public static String getRandomLottery() {
        return lottery[getRandom(lottery.length)];
    }

    public static void setLottery(String[]newLotterys){
        lottery = newLotterys;
    }

    public static Winger getWinnerResponse(){
        Winger winningResponse = new Winger();
        winningResponse.setName(getRandomLottery());
        winningResponse.setActivityName(getRandomMoney());
        winningResponse.setActivityName(getRandomName());
        return winningResponse;
    }

    public static List<Winger> getWinners(int num){
        List<Winger> winners = new ArrayList<>();
        for(int i=0; i<num; i++){
            winners.add(getWinnerResponse());
        }
        return winners;
    }

}
