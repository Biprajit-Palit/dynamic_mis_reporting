export type DropdownOption = {
  value: number | string;
  label: string;
};

export type InputFilter = {
  name: string;
  label: string;

  type:
    | "textbox"
    | "dropdown"
    | "date";

  options?: DropdownOption[];
};

export type OutputColumn = {
  column: string;
  label: string;
};

export type DynamicReport = {
  input_filters: InputFilter[];

  output_columns: OutputColumn[];
};

export type StudentRecord = {
  student_roll_no: string;

  student_name: string;

  department_name: string;

  marks: number;
};