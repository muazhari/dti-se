'use client'
import React from "react";
import {Button} from "@nextui-org/button";
import {Link} from "@nextui-org/link";


export default function Home() {

    return (
        <>
            <Button as={Link} href="/exercise-1" className="border-2 m-2 p-2">exercise-1</Button>
        </>
    );
}
