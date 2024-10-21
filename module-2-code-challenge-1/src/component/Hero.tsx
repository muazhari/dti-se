'use client'
import React from "react";
import {Swiper, SwiperSlide} from "swiper/react";
import {Pagination} from "swiper/modules";
import Image from "next/image";

export default function Hero() {
    return (
        <Swiper
            loop={true}
            autoplay={true}
            modules={[Pagination]}
            spaceBetween={30}
            slidesPerView={1.5}
            pagination={{clickable: true}}
        >
            {
                [1, 2, 3].map((item, index) => (
                    <SwiperSlide key={index}>
                        <div className="relative w-full h-[27rem]">
                            <Image
                                className="rounded-xl"
                                src={`https://placehold.co/200x400?text=hero${item}`}
                                alt="hero"
                                layout="fill"
                                objectFit="cover"
                                priority={true}
                            />
                        </div>
                    </SwiperSlide>
                ))
            }
        </Swiper>
    )
}
