package com.seatbookingsystem.todoappjavap1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TodoApp extends JFrame {

    private static final Color ACCENT     = new Color(99, 102, 241);
    private static final Color BG_APP     = new Color(240, 242, 245);
    private static final Color BG_CARD    = Color.WHITE;
    private static final Color TEXT_TITLE = new Color(26, 26, 46);

    private final List<Todo> todos = new ArrayList<>();
    private final JPanel listPanel;
    private final JLabel statsLabel;
    private final JTextField inputField;

    public TodoApp() {
        super("My Tasks");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(620, 700);
        setMinimumSize(new Dimension(500, 400));
        setLocationRelativeTo(null);

        getContentPane().setBackground(BG_APP);
        setLayout(new BorderLayout());

        // ── Header ──────────────────────────────────────────────────────────
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(BG_APP);
        header.setBorder(new EmptyBorder(32, 32, 16, 32));

        JLabel title = new JLabel("My Tasks");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(TEXT_TITLE);
        title.setAlignmentX(LEFT_ALIGNMENT);

        statsLabel = new JLabel("0 tasks");
        statsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        statsLabel.setForeground(new Color(107, 114, 128));
        statsLabel.setAlignmentX(LEFT_ALIGNMENT);
        statsLabel.setBorder(new EmptyBorder(4, 0, 16, 0));

        // Add-task input row
        JPanel inputRow = new JPanel(new BorderLayout(10, 0));
        inputRow.setBackground(BG_APP);
        inputRow.setAlignmentX(LEFT_ALIGNMENT);
        inputRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));

        inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 2, true),
                new EmptyBorder(8, 14, 8, 14)
        ));
        inputField.setBackground(BG_CARD);

        JButton addBtn = new JButton("+ Add Task") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? ACCENT.darker() : ACCENT);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        addBtn.setForeground(Color.WHITE);
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        addBtn.setBorderPainted(false);
        addBtn.setContentAreaFilled(false);
        addBtn.setFocusPainted(false);
        addBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addBtn.setPreferredSize(new Dimension(120, 42));

        inputRow.add(inputField, BorderLayout.CENTER);
        inputRow.add(addBtn,     BorderLayout.EAST);

        header.add(title);
        header.add(statsLabel);
        header.add(inputRow);

        // ── List panel (scrollable) ──────────────────────────────────────────
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(BG_APP);
        listPanel.setBorder(new EmptyBorder(4, 32, 16, 32));

        JScrollPane scroll = new JScrollPane(listPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.setBackground(BG_APP);
        scroll.getViewport().setBackground(BG_APP);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(header, BorderLayout.NORTH);
        add(scroll,  BorderLayout.CENTER);

        // ── Event handlers ───────────────────────────────────────────────────
        ActionListener addAction = e -> addTask();
        addBtn.addActionListener(addAction);
        inputField.addActionListener(addAction);

        refreshList();
    }

    private void addTask() {
        String text = inputField.getText().trim();
        if (text.isEmpty()) {
            inputField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(239, 68, 68), 2, true),
                    new EmptyBorder(8, 14, 8, 14)
            ));
            Timer t = new Timer(1500, e -> inputField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(229, 231, 235), 2, true),
                    new EmptyBorder(8, 14, 8, 14)
            )));
            t.setRepeats(false);
            t.start();
            return;
        }
        todos.add(new Todo(text));
        inputField.setText("");
        refreshList();
    }

    private void deleteTask(Todo todo) {
        todos.remove(todo);
        refreshList();
    }

    private void refreshList() {
        listPanel.removeAll();

        if (todos.isEmpty()) {
            listPanel.add(buildEmptyState());
        } else {
            for (Todo todo : todos) {
                JPanel card = wrapInCard(new TaskRow(todo, this::refreshList, this::deleteTask));
                listPanel.add(card);
                listPanel.add(Box.createVerticalStrut(10));
            }
        }

        long done = todos.stream().filter(Todo::isCompleted).count();
        statsLabel.setText(todos.size() + " task" + (todos.size() == 1 ? "" : "s") +
                (done > 0 ? "  •  " + done + " completed" : ""));

        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel wrapInCard(TaskRow row) {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.setColor(new Color(229, 231, 235));
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        card.add(row, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildEmptyState() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(48, 0, 48, 0));

        JLabel icon = new JLabel("✓") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(209, 213, 219));
                g2.fillOval(0, 4, 56, 56);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 28));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString("✓", (56 - fm.stringWidth("✓")) / 2,
                        4 + (56 + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        icon.setPreferredSize(new Dimension(56, 64));
        icon.setAlignmentX(CENTER_ALIGNMENT);

        JLabel msg = new JLabel("No tasks yet — add one above!");
        msg.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        msg.setForeground(new Color(156, 163, 175));
        msg.setAlignmentX(CENTER_ALIGNMENT);
        msg.setBorder(new EmptyBorder(12, 0, 0, 0));

        panel.add(icon);
        panel.add(msg);
        return panel;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new TodoApp().setVisible(true));
    }
}
