import React from "react";
import type {DeleteModalProps} from "~/types/common";



const DeleteModal = ({ onConfirm, onCancel }: DeleteModalProps) => {
    return (
        <div className="fixed inset-0 bg-black bg-opacity-40 flex items-center justify-center z-50">
            <div className="bg-white p-6 rounded-lg shadow-lg max-w-sm w-full">
                <h2 className="text-xl font-semibold mb-4 text-gray-800">정말 삭제하시겠습니까?</h2>
                <div className="flex justify-end space-x-4">
                    <button
                        onClick={onCancel}
                        className="px-4 py-2 bg-gray-300 text-gray-800 rounded hover:bg-gray-400"
                    >
                        아니요
                    </button>
                    <button
                        onClick={onConfirm}
                        className="px-4 py-2 bg-red-600 text-white rounded hover:bg-red-700"
                    >
                        예
                    </button>
                </div>
            </div>
        </div>
    );
};

export default DeleteModal;
