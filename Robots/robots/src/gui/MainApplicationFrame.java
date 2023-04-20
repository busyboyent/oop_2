package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.*;

import log.Logger;

/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается.
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 *
 */
public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();

    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width  - inset*2,
                screenSize.height - inset*2);

        setContentPane(desktopPane);



        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400,  400);
        addWindow(gameWindow);

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent we) {
            close(); }});

    }

    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }

    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

//    protected JMenuBar createMenuBar() {
//        JMenuBar menuBar = new JMenuBar();
//
//        //Set up the lone menu.
//        JMenu menu = new JMenu("Document");
//        menu.setMnemonic(KeyEvent.VK_D);
//        menuBar.add(menu);
//
//        //Set up the first menu item.
//        JMenuItem menuItem = new JMenuItem("New");
//        menuItem.setMnemonic(KeyEvent.VK_N);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_N, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("new");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
//
//        //Set up the second menu item.
//        menuItem = new JMenuItem("Quit");
//        menuItem.setMnemonic(KeyEvent.VK_Q);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("quit");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
//
//        return menuBar;
//    }

    private JMenuBar generateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();


        var lookAndFeelMenu = createJMenu("Режим отображения",
                KeyEvent.VK_V, "Управление режимом отображения приложения");
        var testMenu = createJMenu("Тесты", KeyEvent.VK_T, "Тестовые команды");
        var programMenu = createJMenu("Программа", KeyEvent.VK_B, "Помощь");
        {
            var systemLookAndFeel = createJMenuItem("Системная схема", KeyEvent.VK_S,
                    (event) -> {
                        setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        this.invalidate(); });
            lookAndFeelMenu.add(systemLookAndFeel);

            var crossplatformLookAndFeel = createJMenuItem("Универсальная схема", KeyEvent.VK_S,
                    (event) -> {
                        setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                        this.invalidate();});
            lookAndFeelMenu.add(crossplatformLookAndFeel);

            var addLogMessageItem = createJMenuItem("Сообщение в лог", KeyEvent.VK_S,
                    (event) -> {Logger.debug("Новая строка");});
            testMenu.add(addLogMessageItem);

            var exit = createJMenuItem("Выход", KeyEvent.VK_E, (event) -> { close();

            });
            programMenu.add(exit);
        }

        menuBar.add(programMenu);
        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);

        return menuBar;
    }

    private void close(){
        UIManager.put("OptionPane.yesButtonText"   , "Да"    );
        UIManager.put("OptionPane.noButtonText"    , "Нет"   );
        var result = JOptionPane.showConfirmDialog(this,
                "Вы Уверены?",
                "ОКНО ВЫХОДА",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if(result == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }

    private JMenuItem createJMenuItem(String text, int mnemonic, ActionListener action){
        var jMenuItem = new JMenuItem(text, mnemonic);
        jMenuItem.addActionListener(action);
        return jMenuItem;
    }

    private JMenu createJMenu(String menuName, int mnemonic, String accessibleDescription){
        var jmenu = new JMenu(menuName);
        jmenu.setMnemonic(mnemonic);
        jmenu.getAccessibleContext().setAccessibleDescription(
                accessibleDescription);
        return jmenu;
    }

    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
               | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }
}
