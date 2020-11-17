/*
Copyright (c) 2015-2017 Divested Computing Group

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package info.spotcomms.trammel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created using IntelliJ IDEA
 * User: Tad
 * Date: 6/9/15
 * Time; 2:24 PM
 */
public class Editor extends JFrame {

    private JPanel panContent;
    private JTextArea txtEditor;
    private JButton btnSave;

    public Editor(String title, File fleIn) {
        setTitle("Trammel - Editing: " + title);
        setLocation(150, 150);
        setContentPane(panContent);
        pack();
        setVisible(true);

        try {
            Scanner fileLoader = new Scanner(fleIn);
            while (fileLoader.hasNext()) {
                txtEditor.append(fileLoader.nextLine() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnSave.addActionListener(actionEvent -> {
            try {
                PrintWriter writer = new PrintWriter(fleIn, "UTF-8");
                writer.write(txtEditor.getText());
                writer.close();
                dispose();
            } catch (Exception z) {
                z.printStackTrace();
            }
        });
    }

    {
        // GUI initializer generated by IntelliJ IDEA GUI Designer
        // >>> IMPORTANT!! <<<
        // DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panContent = new JPanel();
        panContent.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        txtEditor = new JTextArea();
        panContent.add(txtEditor, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(300, 300), new Dimension(300, 300), null, 0, false));
        btnSave = new JButton();
        btnSave.setText("Save and Close");
        panContent.add(btnSave, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(350, 20), new Dimension(350, 20), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panContent;
    }
}
