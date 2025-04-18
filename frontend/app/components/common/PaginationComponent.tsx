import React from "react";
import type {PaginationProps} from "~/types/common";


function PaginationComponent({ page, size, start, end, prev, next, movePage }: PaginationProps) {
    const pageNumbers = [];
    for (let i = start; i <= end; i++) {
        pageNumbers.push(i);
    }

    return (
        <div className="flex justify-center mt-6 space-x-1">
            {prev && (
                <button className="px-3 py-1 bg-gray-300 rounded" onClick={() => movePage(start - 1)}>
                    &lt;
                </button>
            )}
            {pageNumbers.map((num) => (
                <button
                    key={num}
                    className={`px-3 py-1 rounded ${num === page ? "bg-blue-500 text-white" : "bg-gray-200 hover:bg-gray-300"}`}
                    onClick={() => movePage(num)}
                >
                    {num}
                </button>
            ))}
            {next && (
                <button className="px-3 py-1 bg-gray-300 rounded" onClick={() => movePage(end + 1)}>
                    &gt;
                </button>
            )}
        </div>
    );
}

export default PaginationComponent;