import React from "react";
import Image from "next/image";
import {useRouter} from "next/navigation";

const Contact: React.FC = () => {
    const router = useRouter();
    return (
        <section id="contact" className="bg-[#0B0C0E] text-[#F4F7FA] py-[5rem]">
            <div className="container mx-auto">
                <div className="flex justify-between items-center">
                    <div>
                        <h2 className="text-[3.75rem] font-medium">
                            Have something in mind?
                        </h2>
                        <div className="flex items-center gap-[0.75rem] mt-[0.5rem]">
                            <Image
                                src="https://cdn.builder.io/api/v1/image/assets/TEMP/47cb2946998f9c7da7a50591a731e7563543bae6c53facd7aaff8969b7e6c028?apiKey=e946ad7206da4373a899cf38e79aca51&"
                                alt=""
                                width={80}
                                height={80}
                                className="rounded-full"
                            />
                            <p className="text-[3.75rem] font-medium">
                                {`let's build it together.`}
                            </p>
                        </div>
                    </div>
                    <button
                        onClick={() => router.push("/exercise-1/contact")}
                        className="bg-[#F4F7FA] text-[#0B0C0E] text-[1.125rem] font-medium py-[1.5rem] px-[3rem] rounded-[10.625rem]">
                        Get in touch
                    </button>
                </div>
            </div>
        </section>
    );
};

export default Contact;
