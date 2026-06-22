-- Link course dropdown to department and include department_id for dependent filtering
UPDATE dynamic_report
SET input_filters = (
    SELECT jsonb_agg(
        CASE
            WHEN elem->>'name' = 'course_id' THEN
                elem
                || jsonb_build_object(
                    'depends_on', 'department_id',
                    'dropdown_query',
                    'SELECT course_id AS value, course_name AS label, department_id AS department_id FROM course ORDER BY course_name'
                )
            ELSE elem
        END
    )
    FROM jsonb_array_elements(input_filters) AS elem
)
WHERE report_id = 1;
