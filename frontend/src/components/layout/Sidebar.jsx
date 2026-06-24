const navItems = [

  { label: "Student MIS", active: true },
  
];

export default function Sidebar() {
  return (
    <aside className="flex w-72 shrink-0 flex-col justify-between border-r border-border-sidebar bg-sidebar p-6 shadow-sidebar">
      <div>
        <div className="mb-10">
          <img
            src="/mislogo.png"
            alt="NIC MIS Reporting"
            className="mb-3 h-14 w-auto rounded-xl bg-white p-1"
          />
          <h1 className="text-xl font-bold tracking-wide text-text-inverse">
            MIS Reporting
          </h1>
          <p className="mt-1 text-sm text-text-sidebar-muted">
            Management Information System
          </p>
        </div>

        <nav className="space-y-1">
          {navItems.map((item) => (
            <button
              key={item.label}
              type="button"
              className={`w-full rounded-xl px-4 py-3 text-left text-sm font-medium transition-all duration-200 ${
                item.active
                  ? "bg-sidebar-active text-white shadow-elevated"
                  : "text-text-sidebar hover:bg-sidebar-hover hover:text-text-inverse"
              }`}
            >
              {item.label}
            </button>
          ))}
        </nav>
      </div>

      <div className="rounded-2xl border border-border-sidebar bg-sidebar-hover p-4">
        <p className="text-xs text-text-sidebar-muted">Logged in as</p>
        <h2 className="mt-1 text-sm font-semibold text-text-inverse">
          System Administrator
        </h2>
        <p className="mt-1 text-xs text-text-sidebar-muted">
          Enterprise Access Level
        </p>
      </div>
    </aside>
  );
}
