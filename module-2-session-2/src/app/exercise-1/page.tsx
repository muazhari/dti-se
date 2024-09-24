import "./style.scss"
import React from "react";
import Image from "next/image"
import logo from "@/public/images/logo.svg"
import whatsappIcon from "@/public/images/whatsapp-icon.svg"
import image from "@/public/images/img.svg"
import balls from "@/public/images/balls.svg"

export default function Exercise1() {
    return (
        <div className="exercise-1">
            <div className="page">
                <nav>
                    <a href="#" id="logo">
                        <Image src={logo} alt="trainme logo"/>
                    </a>
                    <ul>
                        <li><a href="#">Home</a></li>
                        <li><a href="#">About</a></li>
                        <li><a href="#">Training</a></li>
                    </ul>
                </nav>

                <main>
                    <section className="text">
                        <h1>WORKOUTS MADE <br/> <span>EXCLUSIVE</span> FOR YOU!</h1>
                        <p>We create <strong>exclusive and unique</strong> workouts for you. <br/>
                            Invest in your body and get <strong>much more performance</strong><br/>and quality of life.
                        </p>
                        <button alt="go to whatsapp">
                            <Image src={whatsappIcon} alt="whatsapp icon"/>
                            Start now
                        </button>
                    </section>
                    <Image src={image} alt="illustration of a woman doing workout on a gym"/>
                </main>

                <footer>
                    send us a message <a href="mailto:#">@trainme</a>
                </footer>

            </div>
            <Image id="balls" src={balls} alt="decorative balls on the bottom left os the page"/>
        </div>
    );
}
