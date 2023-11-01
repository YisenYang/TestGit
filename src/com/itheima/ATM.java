package com.itheima;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ATM {
    private ArrayList<Account> accounts = new ArrayList<>();//[]
    private Account loginAcc;
    Scanner sc =new Scanner(System.in);


   /**启动ATM系统 展示欢迎界面*/
    public void start(){
        while (true) {
            System.out.println("===欢迎您进入到了ATM系统===");
            System.out.println("1.用户登录");
            System.out.println("2.用户开户");
            System.out.println("请选择：");
            int command = sc.nextInt();
            switch (command){
                case 1:
                    //用户登录
                    login();
                    break;
                case 2:
                    //用户开户
                    createAccount();
                    break;
                default:
                    System.out.println("没有该操作");
            }
        }
    }
    /** 完成用户开户  用户输入账户，性别，密码，并确认密码，取现额度*/
    private void createAccount(){
        // 1.创建一个账户对象，用于封装用户的开户信息
        Account acc = new Account();

        //2.需要用户输入自己的开户信息，并赋值给账户对象

            System.out.println("请您输入您的账户名称：");
            String name = sc.next();
            acc.setUserName(name);

        while (true) {
            System.out.println("请您输入您的性别：");
            char sex = sc.next().charAt(0);
            if(sex == '男' || sex == '女'){
                acc.setSex(sex);
                break;
            }else {
                System.out.println("您输入的性别有误，请您重新输入");
            }
        }

        while (true) {
            System.out.println("请您输入您的密码：");
            String passWord = sc.next();
            System.out.println("请您确认您的密码：");
            String okPassword = sc.next();
            if(okPassword.equals(passWord)) {
                acc.setPassWord(passWord);
                break;
            }else {
                System.out.println("您两次输入的密码不一致，请您重新输入");
            }
        }

        System.out.println("请您输入您的取现额度：");
        double limit = sc.nextDouble();
        acc.setLimit(limit);

        String newCardId = createCardId();
        acc.setCardId(newCardId);

        //3.将该账户对象存到集合中去
        accounts.add(acc);
        System.out.println("恭喜您，" + acc.getUserName() +"开户成功，您的卡号为：" + acc.getCardId());



    }

    //卡号循环
    private String createCardId(){
        while (true) {
            //1.定义一个String类型的变量记住8位数字作为一个卡号
            String cardId = "";

            //2.使用循环，循环8次，每次产生一个随机数给cardId连接起来
            Random r = new Random();
            for (int i = 0; i < 8; i++) {
                int data = r.nextInt(10);//0-9
                cardId += data;
            }
            //判断cardId中记住的卡号，是否与其他账户的卡号重复，没有重复，才可以作为一个新的卡号返回
            Account acc = getAccountByCardId(cardId);
            if(acc==null){
                //cardId没有找到账户对象，因此cardId没有其他账户的卡号重复，可以返回他作为一个新卡号
                return cardId;
            }
        }

    }

    /** 完成用户的登录操作*/
    private void login(){
        System.out.println("====系统登陆操作====");
        //1.判断系统中是否存在账户对象，存在才能登陆，如果不存在，我们直接结束登录操作
        if(accounts.isEmpty()){
            System.out.println("系统中没有账户，请您先去开户");
            return;
        }
        //2，系统中存在账户，可以进行登陆操作
        while (true) {
            System.out.println("请您输入您的卡号：");
            String cardId = sc.next();
            //3.判断这个卡号是否存在
            Account acc = getAccountByCardId(cardId);
            if(acc==null){
                System.out.println("您输入的登陆卡号不存在");
            }else {
                //账户存在，输入密码
                while (true) {
                    System.out.println("请您输入登陆密码：");
                    String passWord = sc.next();
                    if(passWord.equals(acc.getPassWord())){
                        loginAcc = acc;
                        System.out.println("恭喜您，" + acc.getUserName() + "成功登录系统");
                        //展示登录后的操作界面
                        showUserCommand();
                        return;
                    }else {
                        System.out.println("您输入的密码不正确，请重新输入");
                    }
                }

            }
        }


    }

    private void showUserCommand(){
        while (true) {
            System.out.println(loginAcc.getUserName() + "====您可以选择如下功能进行账户的处理====");
            System.out.println("1.查询账户");
            System.out.println("2.存款");
            System.out.println("3.取款");
            System.out.println("4.转账");
            System.out.println("5.密码修改");
            System.out.println("6.退出");
            System.out.println("7.注销当前账户");
            int command = sc.nextInt();
            switch (command){
                case 1:
                    //查询
                    showLoginAccount();
                    break;
                case 2:
                    //存款
                    depositMoney();
                    break;
                case 3:
                    //取款
                    drawMoney();
                    break;
                case 4:
                    //转账
                    break;
                case 5:
                    //密码修改
                    break;
                case 6:
                    //退出
                    System.out.println(loginAcc.getUserName() + "您退出系统成功");
                    return;//跳出并结束当前方法
                case 7:
                    //注销当前账户
                    break;
                default:
                    System.out.println("您当前的选择不正确，请重新选择");
            }
        }

    }

    private void drawMoney() {
        System.out.println("====取钱操作====");
        //1.判断账户余额是否大于100元，如果不够，不让用户取钱
        if(loginAcc.getMoney() < 100){
            System.out.println("您的账户余额不足100元，不允许取钱");
            return;
        }
        //2.让用户输入取款金额

        while (true) {
            System.out.println("请您输入取款金额：");
            double money = sc.nextDouble();

            //3.判断账户余额是否足够

            if(loginAcc.getMoney() >= money){
                //账户余额足够
                //4.判断当前取款金额是否超过了每次限额
                if(money > loginAcc.getLimit()){
                    System.out.println("您当前取款金额超过了每次限额，您每次最多可取：" + loginAcc.getLimit());
                }else {
                    //代表可以开始取钱了，更新当前账户的余额信息
                    loginAcc.setMoney(loginAcc.getMoney() - money);
                    System.out.println("您取款："+ money + "成功，取款后您剩余：" + loginAcc.getMoney());
                    break;
                }

            }else {
                System.out.println("余额不足，您的账户中的余额是：" + loginAcc.getMoney());
            }
        }


    }

    /**存钱操作*/
    private void depositMoney() {
        System.out.println("====存钱操作====");
        System.out.println("请您输入存款金额：");
        double money = sc.nextDouble();
        //更新当前登录的账户的余额
        loginAcc.setMoney(loginAcc.getMoney() + money);
        System.out.println("恭喜您，您存钱，" + money + "成功，存钱后的余额为：" + loginAcc.getMoney());
    }

    /** 展示查询信息*/
    private void showLoginAccount(){
        System.out.println("====当前您的账户信息如下====");
        System.out.println("卡号：" + loginAcc.getCardId());
        System.out.println("户主：" + loginAcc.getUserName());
        System.out.println("性别：" + loginAcc.getSex());
        System.out.println("余额：" + loginAcc.getMoney());
        System.out.println("每次取现的额度：" +loginAcc.getLimit());
    }
    /**根据卡号查询账户对象返回account = [c1, c2, c3...]*/
    private Account getAccountByCardId(String cardId){
        //遍历全部对象
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            //判断这个账户对象acc中的卡号是否是我们要找的卡号
            if(acc.getCardId().equals(cardId)){
                return acc;
            }
        }
        return null;//查无账号，这个卡号不存在
    }

}
