package tool.view;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import tool.common.ToolRobot;

import java.awt.Graphics;

public interface ComponentView {
    void paintComponent(Graphics var1);

    int numberUpdates();

    ToolRobot getModel();

    void clearChanged();
}
