export default function Pagination() {

  return (
    <div className="flex justify-end gap-3 mt-6">

      <button className="px-4 py-2 rounded-xl bg-white/5 border border-white/10">
        Previous
      </button>

      <button className="px-4 py-2 rounded-xl bg-blue-600">
        1
      </button>

      <button className="px-4 py-2 rounded-xl bg-white/5 border border-white/10">
        2
      </button>

      <button className="px-4 py-2 rounded-xl bg-white/5 border border-white/10">
        Next
      </button>

    </div>
  );
}