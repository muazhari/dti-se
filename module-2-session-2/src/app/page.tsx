import React from "react";
import Link from "next/link";

export default function Home() {
    return (
        <>
            <Link href={"/exercise-1"}>
                <button className="border-2 m-2 p-2">exercise-1</button>
            </Link>
            <Link href={"/exercise-2"}>
                <button className="border-2 m-2 p-2">exercise-2</button>
            </Link>
        </>
    );
}
