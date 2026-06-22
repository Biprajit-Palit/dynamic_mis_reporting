const fieldClassName =
  "w-full rounded-xl border border-border bg-input px-4 py-3 text-sm text-text outline-none transition placeholder:text-text-muted focus:border-primary focus:ring-2 focus:ring-primary/20 [color-scheme:light] disabled:cursor-not-allowed disabled:opacity-60";

function getParentFilterName(field) {
  if (field.depends_on) {
    return field.depends_on;
  }

  if (
    field.name === "course_id" &&
    field.options?.some((option) => option.parentId != null)
  ) {
    return "department_id";
  }

  return undefined;
}

function getVisibleOptions(field, filters) {
  if (!field.options) {
    return [];
  }

  const parentKey = getParentFilterName(field);
  if (!parentKey) {
    return field.options;
  }

  const parentValue = filters[parentKey];
  if (parentValue === "" || parentValue === undefined) {
    return [];
  }

  const parentId = Number(parentValue);

  return field.options.filter(
    (option) => Number(option.parentId) === parentId,
  );
}

function getParentLabel(parentKey) {
  return parentKey.replace(/_id$/, "").replace(/_/g, " ");
}

export default function DynamicField({ field, value, filters, onChange }) {
  if (field.type === "dropdown") {
    const parentKey = getParentFilterName(field);
    const options = getVisibleOptions(field, filters);
    const isDisabled =
      Boolean(parentKey) &&
      (filters[parentKey] === "" || filters[parentKey] === undefined);

    return (
      <select
        aria-label={field.label}
        value={value}
        disabled={isDisabled}
        onChange={(e) => {
          const nextValue = e.target.value;

          onChange(field.name, nextValue === "" ? "" : Number(nextValue));
        }}
        className={fieldClassName}
      >
        <option value="">
          {isDisabled && parentKey
            ? `Select ${getParentLabel(parentKey)} first`
            : `Select ${field.label}`}
        </option>

        {options.map((option) => (
          <option key={option.value} value={option.value}>
            {option.label}
          </option>
        ))}
      </select>
    );
  }

  return (
    <input
      type={
        field.type === "date"
          ? "date"
          : field.type === "number"
            ? "number"
            : "text"
      }
      value={value}
      onChange={(e) => {
        const nextValue = e.target.value;

        onChange(
          field.name,
          field.type === "number"
            ? nextValue === ""
              ? ""
              : Number(nextValue)
            : nextValue,
        );
      }}
      placeholder={field.label}
      className={fieldClassName}
    />
  );
}
