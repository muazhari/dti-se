import React from "react";

interface FormInputProps {
    label: string;
    placeholder: string;
    type?: string;
    isTextArea?: boolean;
}

const FormInput: React.FC<FormInputProps> = ({
                                                 label,
                                                 placeholder,
                                                 type = "text",
                                                 isTextArea = false,
                                             }) => {
    const inputId = `${label.toLowerCase()}-input`;

    return (
        <div className="flex flex-col mt-8">
            <label
                htmlFor={inputId}
                className="text-lg leading-relaxed text-neutral-950"
            >
                {label}
            </label>
            {isTextArea ? (
                <textarea
                    id={inputId}
                    placeholder={placeholder}
                    className="mt-3 text-3xl leading-snug opacity-60 text-neutral-700 bg-transparent resize-none"
                    rows={4}
                />
            ) : (
                <input
                    type={type}
                    id={inputId}
                    placeholder={placeholder}
                    className="mt-3 text-3xl leading-snug opacity-60 text-neutral-700 bg-transparent"
                />
            )}
            <div className="mt-3 border border-solid bg-neutral-950 border-neutral-950 min-h-[0.1rem]"/>
        </div>
    );
};

export default FormInput;
