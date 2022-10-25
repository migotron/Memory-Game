import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
/**
 * @author mibarr19
 */
public class App extends JFrame
{
    private int [] width = {4, 5, 6};
    private int [] length = {4, 4, 4}; 
    private int WINDOW_WIDTH = 1435, WINDOW_HEIGHT = 860;
    private int hold = 0, cardflip1, cardflip2, track = 1;
    private JPanel difpan = new JPanel();
    private JPanel Cards = new JPanel();
    private JButton [] difbut = new JButton[3];
    private JButton [] cardbuts;
    private ImageIcon [] cardimages;
    private ImageIcon back = new ImageIcon("background.jpg");
    private String [] imagesnames;
    private String [] levels = {"EASY", "MEDIUM", "HARD"};   
    private String [] images = {"aa12.jpg","acr68.jpg","ak47.jpg",
                                "barrett50cal.jpg","m4a1.jpg","m60e4.jpg",
                                "mp5k.jpg","msr.jpg","p90.jpg","rpg7.jpg",
                                "smaw.jpg","spas12.jpg"};
    
    public App()
    {
        super("Memory Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        add(difpan, BorderLayout.SOUTH);
        
        difpan.setBackground(Color.BLACK);
        Cards.setBackground(Color.BLACK);
        
        for(int l = 0; l < 3; l++)
        {
            difbut[l] = new JButton(levels[l]);
                   difbut[l].setBackground(Color.BLACK);
                   difbut[l].setForeground(Color.RED);
                  
            difpan.add(difbut[l]);
            difbut[l].addActionListener(new App.difbutListener());
        }
        
        //display window
        pack();
        setVisible(true);
    }
    private class difbutListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            for(int x = 0; x < 3; x++)
            {
                if(difbut[x] == e.getSource()) //check all buttons of difficulty
                {
                    CardBut(length[x], width[x]);
                    flip(length[x], width[x]);
                    
                    hold = x;
                    for(int can = 0; can < 3; can++)
                    {
                        difbut[can].setEnabled(false); // if found, all difficulty buttons get disabled
                    }
                    Toolkit tk = Toolkit.getDefaultToolkit();  
                    int xSize = ((int) tk.getScreenSize().getWidth());  
                    int ySize = ((int) tk.getScreenSize().getHeight());  
                    difpan.setVisible(false);
                    add(Cards, BorderLayout.CENTER);
                    setSize(xSize,ySize);
                }
            }
        }
    }
    private class playgame implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            for(int x = 0; x < (length[hold]*width[hold]); x++)
            {
                if(e.getSource() == cardbuts[x])
                {
                    int NEW_HEIGHT = cardbuts[x].getHeight();
                    int NEW_WIDTH = cardbuts[x].getWidth();
                    Image img = cardimages[x].getImage() ;  
                    Image newimg = img.getScaledInstance( NEW_WIDTH, NEW_HEIGHT,  java.awt.Image.SCALE_SMOOTH ) ;  
                    cardimages[x] = new ImageIcon( newimg );
                    cardbuts[x].setIcon(cardimages[x]);
                    
                    if(track%2 !=0)
                    {
                        cardflip1 = x; 
                        track++;
                    }
                    
                    else if(cardflip1 != x && track%2 == 0)
                    {
                        cardflip2 = x;
                        checkcards(cardflip1, cardflip2); 
                        track++;
                    }
                }
            }
        }
    }
    public void CardBut(int l, int w)
    {
        int totalcards = l*w;
        
        Cards.setLayout(new GridLayout(l, w));
        cardbuts = new JButton[totalcards];
        
        for(int x = 0; x < totalcards; x++)
        {
            cardbuts[x] = new JButton();
            cardbuts[x].setBackground(Color.WHITE);                    
            cardbuts[x].setIcon(back);
            cardbuts[x].addActionListener(new App.playgame());
            Cards.add(cardbuts[x]);
        }
    }
    public void flip(int l, int w)
    {
        Random ran = new Random();
        int totalcards = l * w;
        int halfarea = (totalcards/2);
        int num, count;
        String picname;
        String zero = "blank";
        
        cardimages = new ImageIcon[totalcards];
        imagesnames = new String [totalcards];
        
        for(int x = 0; x < totalcards; x++)
        {
            imagesnames[x] = zero;
        }
        while(imagesnames[totalcards-1].equals(zero))
        {
            num = ran.nextInt(halfarea);
            picname = images[num];

            count = 0;

            for(int y = 0; y < totalcards; y++)
            {
                if(imagesnames[y].equals(picname))
                {
                    count++;
                    if(count == 2)
                    {
                        y = totalcards;
                    }
                }
                else if(imagesnames[y].equals(zero))
                {
                    imagesnames[y] = picname; 
                    y = totalcards;
                }
            }

            for(int z = 0; z < totalcards; z++)
            {
                picname = imagesnames[z];
                cardimages[z] = new ImageIcon(picname);
            }
            for(int x = 0; x < totalcards; x++)
            {
                if(!cardbuts[x].isEnabled())
                {
                    JOptionPane.showMessageDialog(null, "CONGRATULATIONS!!! YOU HAVE MATCHED ALL THE PAIRS!!");
                }

            }
        }
    }
    public void checkcards(int card1, int card2)
    {
        if(imagesnames[card1].equals(imagesnames[card2]))
        {
            JOptionPane.showMessageDialog(null, "Pair found!\nLet's find another one now!");
            cardbuts[card1].setEnabled(false);
            cardbuts[card2].setEnabled(false);
        }
        else 
        {
            JOptionPane.showMessageDialog(null, "No pair found!\nTry  again!");
            cardbuts[card1].setIcon(back);
            cardbuts[card2].setIcon(back);
        }
    }
    
    
    public static void main(String[] args) 
    {
        App homework9;
        homework9 = new App();
        System.out.print(homework9);
    }
}
