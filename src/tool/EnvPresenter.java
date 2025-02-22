package tool;

import tool.common.Position;
import tool.common.ToolEnvironment;
import tool.view.FieldView;
import tool.view.RobotView;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class EnvPresenter {
    private final ToolEnvironment env;
    private Map<Position, FieldView> fields;
    private List<RobotView> robots;
    private JFrame frame;

    public EnvPresenter(ToolEnvironment var1) {
        this.env = var1;
        this.fields = new HashMap();
        this.robots = new ArrayList();
    }

    public void open() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                this.initialize();
                this.frame.setVisible(true);
            });
        } catch (InvocationTargetException | InterruptedException var2) {
            Logger.getLogger(EnvPresenter.class.getName()).log(Level.SEVERE, (String)null, var2);
        }

    }

    protected void init() {
        try {
            SwingUtilities.invokeAndWait(this::initialize);
        } catch (InvocationTargetException | InterruptedException var2) {
            Logger.getLogger(EnvPresenter.class.getName()).log(Level.SEVERE, (String)null, var2);
        }

    }

    public FieldView fieldAt(Position var1) {
        return (FieldView)this.fields.get(var1);
    }

    private void initialize() {
        this.frame = new JFrame("Robot Environment");
        this.frame.setDefaultCloseOperation(3);
        this.frame.setSize(1000, 500);
        this.frame.setPreferredSize(new Dimension(1000, 500));
        this.frame.setResizable(false);
        int var1 = this.env.rows();
        int var2 = this.env.cols();
        GridLayout var3 = new GridLayout(var1, var2);
        JPanel var4 = new JPanel(var3);

        for(int var5 = 0; var5 < var1; ++var5) {
            for(int var6 = 0; var6 < var2; ++var6) {
                Position var7 = new Position(var5, var6);
                FieldView var8 = new FieldView(this.env, var7);
                var4.add(var8);
                this.fields.put(var7, var8);
            }
        }

        this.env.robots().forEach((var1x) -> {
            RobotView var = new RobotView(this, var1x);
            this.robots.add(var);
        });
        this.frame.getContentPane().add(var4, "Center");
        this.frame.pack();
    }

    protected List<FieldView> fields() {
        return new ArrayList(this.fields.values());
    }
}