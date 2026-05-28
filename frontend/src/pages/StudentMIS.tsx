import { useState } from "react";

import DynamicFilterForm
from "../components/mis/DynamicFilterForm";

import ResultTable
from "../components/mis/ResultTable";

import { dynamicReport }
from "../data/mockReport";

import type {
  StudentRecord,
} from "../types/report";

import { searchStudents } from "../api/studentApi";

export default function StudentMIS() {

  const [filters, setFilters] =
    useState<Record<string, string>>({});

  const [data, setData] =
    useState<StudentRecord[]>([]);

    const [loading, setLoading] = useState(false);

    const [error, setError] = useState<string | null>(null);

    const handleSearch = async () => {
      try {
        setLoading(true);
        setError(null);

        const response = await searchStudents(filters);

        setData(response);
      } catch (err) {
        console.error(err);

        setError("Failed to fetch student records");
      } finally {
        setLoading(false);
      }
    };

  return (
    <div>

      <div className="mb-8">

        <h1 className="text-4xl font-bold">
          Dynamic Student MIS
        </h1>

        <p className="text-gray-400 mt-2">
          Enterprise reporting engine
        </p>

      </div>

      <DynamicFilterForm
        filters={filters}
        setFilters={setFilters}
        config={dynamicReport}
        onSearch={handleSearch}
      />

      {error && (
        <div className="rounded-lg bg-red-500/10 p-3 text-red-500">
          {error}
        </div>
      )}

      <ResultTable
        columns={dynamicReport.output_columns}
        data={data}
      />

    </div>
  );
}