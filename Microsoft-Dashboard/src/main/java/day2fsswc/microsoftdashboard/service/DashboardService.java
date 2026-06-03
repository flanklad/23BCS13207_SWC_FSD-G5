package day2fsswc.microsoftdashboard.service;

import day2fsswc.microsoftdashboard.model.DashboardItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    public String getTotalUsers()   { return "12,483"; }
    public String getActiveTasks()  { return "247"; }
    public String getRevenue()      { return "$48.2K"; }
    public String getPerformance()  { return "94.2%"; }

    public List<String> getRecentActivity() {
        return List.of(
            "✓  Priya Sharma completed Task #1047",
            "✓  New user Alex Chen registered",
            "⚠  Server load exceeded 85% threshold",
            "✓  Monthly report generated successfully",
            "✓  Database backup completed",
            "⚠  Payment gateway timeout — auto-retried",
            "✓  5 new support tickets resolved",
            "✓  Security scan completed — no issues found",
            "✓  Deploy to production succeeded (v2.4.1)",
            "⚠  High memory usage on node-3 detected"
        );
    }

    public List<DashboardItem> getDashboardItems() {
        return List.of(
            new DashboardItem("Priya Sharma",   "Engineering",  "Active",   "2026-05-01", "$12,400"),
            new DashboardItem("Alex Chen",      "Marketing",    "Active",   "2026-04-15", "$8,200"),
            new DashboardItem("Jordan Lee",     "Design",       "On Leave", "2026-05-10", "$9,500"),
            new DashboardItem("Sofia Rossi",    "Sales",        "Active",   "2026-04-22", "$14,750"),
            new DashboardItem("Marcus Green",   "Engineering",  "Active",   "2026-05-03", "$11,200"),
            new DashboardItem("Aisha Patel",    "HR",           "Active",   "2026-03-18", "$7,800"),
            new DashboardItem("Luca Bianchi",   "Finance",      "Inactive", "2026-02-11", "$13,000"),
            new DashboardItem("Emma Wilson",    "Engineering",  "Active",   "2026-05-20", "$10,900"),
            new DashboardItem("Noah Martinez",  "Marketing",    "Active",   "2026-04-05", "$8,600"),
            new DashboardItem("Chloe Nguyen",   "Design",       "Active",   "2026-05-15", "$9,100")
        );
    }
}
