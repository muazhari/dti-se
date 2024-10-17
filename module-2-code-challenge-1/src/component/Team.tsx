'use client'
import React from "react";
import {userApi} from "@/store/api";
import {Link, Spinner} from "@nextui-org/react";
import Image from "next/image";

export default function Team() {
    const {data, error, isLoading, refetch} = userApi.useGetRandomUserQuery({results: 7})

    return (
        <div className="container mx-auto p-4 flex flex-col items-center gap-12">
            <h1 className="text-5xl font-bold">Our Team</h1>
            <div className="flex flex-col md:flex-row gap-6 flex-wrap">
                {
                    isLoading ?
                        <Spinner/>
                        :
                        data?.map((user, index) => (
                            <div
                                key={index}
                                className="flex-1 relative flex flex-col justify-between bg-white p-4 rounded-xl shadow-xl border min-h-[15rem] w-[18rem]">
                                <Image
                                    src={`https://placehold.co/100x200?text=user${index}`}
                                    alt="user"
                                    layout="fill"
                                    objectFit="cover"
                                    className="rounded-xl"
                                />
                                <div className="z-10">
                                    <h3 className="text-lg font-semibold">{user.name}, Engineer</h3>
                                    <p className="text-sm text-gray-500">{user.expertise}</p>
                                </div>
                                <Link className="z-10 text-sm text-gray-500" href={`mailto:${user.email}`}>
                                    {user.email}
                                </Link>
                            </div>
                        ))
                }
            </div>
        </div>
    );
}

