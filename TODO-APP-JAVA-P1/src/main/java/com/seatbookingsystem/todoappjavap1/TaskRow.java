package com.seatbookingsystem.todoappjavap1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class TaskRow extends JPanel {

    private static final Color BG_NORMAL    = Color.WHITE;
    private static final Color BG_COMPLETED = new Color(249, 250, 251);
    private static final Color BG_HOVER     = new Color(245, 245, 255);
    private static final Color ACCENT       = new Color(99, 102, 241);
    private static final Color TEXT_NORMAL  = new Color(17, 24, 39);
    private static final Color TEXT_DONE    = new Color(156, 163, 175);
    private static final Color BTN_EDIT     = new Color(99, 102, 241);
    private static final Color BTN_DELETE   = new Color(239, 68, 68);
    private static final Color BTN_SAVE     = new Color(5, 150, 105);
    private static final Color BTN_CANCEL   = new Color(107, 114, 128);

    private final Todo todo;
    private final Runnable onUpdate;
    private final Consumer<Todo> onDelete;

    private final JPanel checkCircle;
    private final JLabel textLabel;
    private final JTextField editField;
    private final JButton editBtn;
    private final JButton saveBtn;
    private final JButton cancelBtn;
    private final JButton deleteBtn;

    public TaskRow(Todo todo, Runnable onUpdate, Consumer<Todo> onDelete) {
        this.todo = todo;
        this.onUpdate = onUpdate;
        this.onDelete = onDelete;

        setLayout(new BorderLayout(12, 0));
        setBorder(new EmptyBorder(12, 16, 12, 16));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        // --- Checkbox circle ---
        checkCircle = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (todo.isCompleted()) {
                    g2.setColor(ACCENT);
                    g2.fillOval(0, 0, 22, 22);
                    g2.setColor(Color.WHITE);
                    g2.setStroke(new BasicStroke(2.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.drawPolyline(new int[]{5, 9, 17}, new int[]{11, 16, 6}, 3);
                } else {
                    g2.setColor(new Color(209, 213, 219));
                    g2.setStroke(new BasicStroke(2f));
                    g2.drawOval(1, 1, 20, 20);
                }
                g2.dispose();
            }
        };
        checkCircle.setPreferredSize(new Dimension(24, 24));
        checkCircle.setOpaque(false);
        checkCircle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        checkCircle.setToolTipText("Toggle complete");
        checkCircle.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                todo.toggle();
                onUpdate.run();
            }
        });

        // --- Text label ---
        textLabel = new JLabel(todo.getText());
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        styleLabel();

        // --- Edit field ---
        editField = new JTextField(todo.getText());
        editField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        editField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, ACCENT));
        editField.setOpaque(false);
        editField.setVisible(false);
        editField.addActionListener(e -> saveEdit());

        // Text area (stacked label + edit field)
        JPanel textArea = new JPanel(new CardLayout()) {
            @Override public Dimension getPreferredSize() {
                return new Dimension(0, super.getPreferredSize().height);
            }
        };
        textArea.setOpaque(false);
        textArea.add(textLabel, "label");
        textArea.add(editField, "edit");

        // Keep references to cards for switching
        editField.putClientProperty("cardLayout", textArea.getLayout());
        editField.putClientProperty("cardParent", textArea);
        textLabel.putClientProperty("cardLayout", textArea.getLayout());
        textLabel.putClientProperty("cardParent", textArea);

        // --- Buttons ---
        editBtn   = makeBtn("Edit",   BTN_EDIT);
        saveBtn   = makeBtn("Save",   BTN_SAVE);
        cancelBtn = makeBtn("Cancel", BTN_CANCEL);
        deleteBtn = makeBtn("Delete", BTN_DELETE);

        saveBtn.setVisible(false);
        cancelBtn.setVisible(false);

        editBtn.addActionListener(e -> startEdit(textArea));
        saveBtn.addActionListener(e -> saveEdit());
        cancelBtn.addActionListener(e -> cancelEdit(textArea));
        deleteBtn.addActionListener(e -> onDelete.accept(todo));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(editBtn);
        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);
        btnPanel.add(deleteBtn);

        add(checkCircle, BorderLayout.WEST);
        add(textArea,    BorderLayout.CENTER);
        add(btnPanel,    BorderLayout.EAST);

        refreshBackground();

        addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { if (!todo.isCompleted()) setBackground(BG_HOVER); }
            @Override public void mouseExited(MouseEvent e)  { refreshBackground(); }
        });
    }

    private void startEdit(JPanel textArea) {
        editField.setText(todo.getText());
        editField.setVisible(true);
        textLabel.setVisible(false);
        ((CardLayout) textArea.getLayout()).show(textArea, "edit");
        editBtn.setVisible(false);
        deleteBtn.setVisible(false);
        saveBtn.setVisible(true);
        cancelBtn.setVisible(true);
        editField.requestFocusInWindow();
        editField.selectAll();
    }

    private void saveEdit() {
        String newText = editField.getText().trim();
        if (!newText.isEmpty()) {
            todo.setText(newText);
        }
        exitEditMode();
        onUpdate.run();
    }

    private void cancelEdit(JPanel textArea) {
        exitEditMode();
    }

    private void exitEditMode() {
        Object parent = editField.getClientProperty("cardParent");
        if (parent instanceof JPanel p) {
            ((CardLayout) p.getLayout()).show(p, "label");
        }
        textLabel.setVisible(true);
        editField.setVisible(false);
        editBtn.setVisible(true);
        deleteBtn.setVisible(true);
        saveBtn.setVisible(false);
        cancelBtn.setVisible(false);
    }

    private void styleLabel() {
        if (todo.isCompleted()) {
            textLabel.setForeground(TEXT_DONE);
            textLabel.setText("<html><strike>" + escapeHtml(todo.getText()) + "</strike></html>");
        } else {
            textLabel.setForeground(TEXT_NORMAL);
            textLabel.setText(todo.getText());
        }
    }

    private void refreshBackground() {
        setBackground(todo.isCompleted() ? BG_COMPLETED : BG_NORMAL);
        styleLabel();
        repaint();
    }

    private static JButton makeBtn(String label, Color fg) {
        JButton btn = new JButton(label) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2.setColor(fg.brighter().brighter());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private static String escapeHtml(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    public void refresh() {
        refreshBackground();
    }
}
