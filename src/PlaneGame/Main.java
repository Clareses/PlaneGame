package PlaneGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends JPanel{

    public static ArrayList<FlyObject> Main_FlyObjects=new ArrayList<>();//我方飞行物的list
    public static ArrayList<FlyObject> Enemy_FlyObjects=new ArrayList<>();//敌方飞行物的list
    public static ArrayList<FlyObject> Award_FlyObjects=new ArrayList<>();//奖励飞行物的list
    public static BufferedImage background;//背景
    public static final int WIDTH=1920;//游戏宽度
    public static final int HEIGHT=1280;//游戏长度
    private final int minTime=10;//最小更新时间（10ms）
    public int clock=0;//计数器
    protected int score=0;//分数
    public MainPlane mainPlane;//我方飞机
    //游戏状态
    private final int Status_Over=0;
    private final int Status_Run=1;
    private final int Status_Pause=2;
    private final int Status_Begin=3;
    private int Status=Status_Begin;

    //随机对象
    Random random=new Random();

    //静态块，绑定背景图片
    static{
        try {
            background=ImageIO.read(Main.class.getResource("background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //start函数，开始游戏进程
    public void Start(){
        mainPlane=init();//初始化我方飞机
        getMousePlace(mainPlane);//飞机与鼠标绑定
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                clock++;
                //如果状态为正在运行，则每单位时间调用更新函数和构造敌机的函数
                if(Status==Status_Run){
                    update();
                    construct();
                }
                //重画
                repaint();
                //判断是否游戏结束，结束则更新状态
                if(mainPlane.life==0){
                    Status=Status_Over;
                    Main_FlyObjects.clear();//清空我方飞行物list
                    for(int i=0;i<Enemy_FlyObjects.size();i++) Enemy_FlyObjects.get(i).gone();//清空敌方飞行物list
                    Award_FlyObjects.clear();//清空奖励飞行物list
                    mainPlane=init();//重新初始化我方飞机
                    getMousePlace(mainPlane);//与鼠标绑定
                }
            }
        },minTime,minTime);
    }

    //初始化飞机函数
    public MainPlane init() {
        MainPlane mainPlane=new MainPlane(new Point(WIDTH/2,WIDTH/2),clock/100);//初始化飞机
        Main_FlyObjects.add(mainPlane);//将飞机添加进飞行物list
        return mainPlane;//返回飞机变量
    }

    //绑定鼠标（传入飞机变量作为参数）
    public void getMousePlace(MainPlane myPlane){
        MouseAdapter mouseAdapter=new MouseAdapter() {

            //如果鼠标移动，则调动我方飞机的setposition函数
            @Override
            public void mouseMoved(MouseEvent e) {
                myPlane.setPosition(new Point(e.getX(),e.getY()));
            }

            //如果鼠标点击，则切换游戏状态
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (Status){
                    case Status_Begin:
                        clock=0;
                        score=0;
                        Status=Status_Run;
                        break;
                    case Status_Run:
                        Status=Status_Pause;
                        break;
                    case Status_Pause:
                        Status=Status_Run;
                        break;
                    case Status_Over:
                        clock=0;
                        score=0;
                        Status=Status_Begin;
                        break;
                }

            }
        };
        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
    }

    //状态更新函数
    public void update(){
        //对我方所有飞行物调用state_update函数，更新状态
        if(!Main_FlyObjects.isEmpty())  for(int i=0;i<Main_FlyObjects.size();i++)  Main_FlyObjects.get(i).State_Update();
        //对敌方所有飞行物调用state_update函数，更新状态，并利用接口回调加分
        if(!Enemy_FlyObjects.isEmpty())  for(int i=0;i<Enemy_FlyObjects.size();i++){
            FlyObject e=Enemy_FlyObjects.get(i);
            if(e.type.equals("Plane")){
                ((EnemyPlane)e).State_Update(new GetScore() {
                    @Override
                    public void Score_set() {
                        score+=((EnemyPlane)e).Score;
                    }
                });
            }else{
                e.State_Update();
            }
        }
        //对奖励list里的所有飞行物调用state_update函数，更新状态
        if(!Award_FlyObjects.isEmpty())  for(int i=0;i<Award_FlyObjects.size();i++) Award_FlyObjects.get(i).State_Update();
    }

    //构造飞行物函数
    public void construct() {
        //控制生成飞行物的速度
        int temp = 30 - clock / 200 <= 8 ? 8 : 30 - clock / 200;
        //控制飞行物生成
        if (clock % temp == 0) {
            //如果生成的数大于990，则生成一个新的奖励飞行物并加入奖励飞行物的list里
            if (random.nextInt(1000)>900){
                Award_FlyObjects.add(new Award(new Point(random.nextInt(WIDTH),0),clock/100));
            //如果生成的数小于990，则生成一个新的敌方飞行物并加入奖励飞行物的list里
            }else{
                //根据生成数的大小控制生成敌机的类型
                int tmp=random.nextInt(100);
                if(tmp>85&&Enemy_L.num<4){
                    Enemy_FlyObjects.add(new Enemy_L(new Point(random.nextInt(WIDTH),0),clock/100));
                }else if(tmp>60){
                    Enemy_FlyObjects.add(new Enemy_M(new Point(random.nextInt(WIDTH),0),clock/100));
                }else{
                    Enemy_FlyObjects.add(new Enemy_S(new Point(random.nextInt(WIDTH),0),clock/100));
                }
            }
        }
    }

    //重写paint函数，根据状态对所有飞行物进行paint，并paint出分数与时间等
    @Override
    public void paint(Graphics graphics){
        switch (Status) {
            case Status_Run:
                graphics.drawImage(background, 0, 0, null);
                graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
                graphics.drawString("分数：" + score, 15, 30);
                graphics.drawString("生命：" + mainPlane.life, 15, 90);
                graphics.drawString("时间：" + clock / 100 + "S", 15, 60);
                FlyObject e;
                for (int i = 0; i <Main_FlyObjects.size(); i++) {
                    e = Main_FlyObjects.get(i);
                    graphics.drawImage(e.getReSource(), e.Position.x - e.width / 2, e.Position.y - e.length / 2, null);
                }
                for (int i = 0; i <Enemy_FlyObjects.size(); i++) {
                    e = Enemy_FlyObjects.get(i);
                    graphics.drawImage(e.getReSource(), e.Position.x - e.width / 2, e.Position.y - e.length / 2, null);
                }
                for (int i = 0; i <Award_FlyObjects.size(); i++) {
                    e = Award_FlyObjects.get(i);
                    graphics.drawImage(e.getReSource(), e.Position.x - e.width / 2, e.Position.y - e.length / 2, null);
                }
                break;

            case Status_Over:
                graphics.drawImage(background, 0, 0, null);
                graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
                graphics.drawString("分数：" + score, 15, 30);
                graphics.drawString("时间：" + clock / 100 + "S", 15, 60);
                graphics.drawString("游戏结束",WIDTH/2-WIDTH/1000*100,HEIGHT/2-100);
                break;

            case Status_Begin:
                graphics.drawImage(background, 0, 0, null);
                graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
                graphics.drawString("开始游戏",WIDTH/2-WIDTH/1000*100,HEIGHT/2-100);
                break;

            case Status_Pause:
                graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
                graphics.drawString("游戏暂停",WIDTH/2-WIDTH/1000*100,HEIGHT/2-100);
                break;
        }
    }

    //main函数，完成窗体初始化并调用start函数
    public static void main(String[] args) {
        JFrame frame = new JFrame("plane_Game");
        Main main=new Main();
        frame.add(main);
        frame.setSize(WIDTH, HEIGHT);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        main.Start();
    }
}
