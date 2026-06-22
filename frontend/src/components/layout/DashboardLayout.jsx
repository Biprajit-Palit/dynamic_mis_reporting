import Sidebar from "./Sidebar";

export default function DashboardLayout({ children }) {
  return (
    <div className="flex min-h-screen bg-app text-text">
      <Sidebar />

      <main className="flex-1 overflow-auto">
        <div className="border-b border-border bg-surface px-8 py-5 shadow-card">
          <p className="text-xs font-semibold uppercase tracking-wider text-primary">
            Reporting Module
          </p>
          <h2 className="mt-1 text-lg font-semibold text-text">
            Student Management Information System
          </h2>
        </div>

        <div className="p-8">{children}</div>
      </main>
    </div>
  );
}
