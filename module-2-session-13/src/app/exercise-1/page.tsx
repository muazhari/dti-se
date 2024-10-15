'use client'
import * as React from "react";
import {Emoji, emojiApi} from "@/app/exercise-1/stores/api";


const Home: React.FC<EmojiSelectorProps> = () => {
    const {data, error, isLoading, refetch} = emojiApi.useGetRandomEmojiQuery()

    const handleEmojiClick = (event: React.MouseEvent<HTMLDivElement>) => {
        refetch()
    }

    return (
        <main className="flex flex-col p-6 mx-auto w-full bg-yellow-50 max-w-[30rem] h-[100vh]">
            <header className="text-xl text-amber-300">
                emojilogy
            </header>
            <section
                className="flex flex-col justify-center bg-amber-200 text-slate-500 h-[100vh] text-center p-4"
            >
                <h1 className="text-4xl py-12">
                    {`What's your emoji today?`}
                </h1>
                <p className="text-xl py-12">Click it!</p>
                {
                    isLoading ? <p className="text-xl">Loading...</p> :
                        <div className="flex flex-col justify-around cursor-pointer" onClick={handleEmojiClick}>
                            <div className="text-9xl py-12">
                                {String.fromCodePoint(parseInt(data.unicode[0].replace("U+", ""), 16))}
                            </div>
                            <div className="text-xl py-12">
                                {data.category}
                            </div>
                        </div>
                }
            </section>
        </main>
    );
};

export default Home;
