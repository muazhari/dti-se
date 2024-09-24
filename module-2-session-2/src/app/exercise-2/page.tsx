import React from 'react';
import './style.scss';
import image from "@/public/images/img.svg"
import Image from "next/image";

export default function Exercise2() {
    return (
        <div className="exercise-2">
            <header className="header">
                <div className="logo">Logo</div>
                <nav className="nav">
                    <ul>
                        <li>Home</li>
                        <li>Shop</li>
                        <li>About Us</li>
                        <li>Contact us</li>
                    </ul>
                </nav>
                <div className="actions">
                    <input type="search" placeholder="Search"/>
                    <div className="cart-icon">Cart</div>
                </div>
            </header>
            <main>
                <section className="about-us">
                    <h1>About Us</h1>
                    <p className="description">
                        Cupcake ipsum dolor sit amet macaroon oat cake.
                    </p>
                    <p className="details">
                        Macaroon muffin sugar plum tiramisu cheesecake marshmallow ice cream
                        gingerbread pie. Dessert gummies apple pie gummi bears danish sweet roll. Candy sesame snaps
                        carrot cake tootsie roll sugar plum gummi bears croissant.
                    </p>
                </section>
                <section className="quote">
                    <blockquote>{`"blah blah blah"`}</blockquote>
                </section>
                <section className="reviews">
                    <h2>Reviews</h2>
                    <div className="review-cards">
                        {[1, 2, 3].map((value, index) => (
                            <div className="review-card" key={index}>
                                <div className="reviewer-image">
                                    <Image src={image} alt="reviewer-image"/>
                                </div>
                                <p className="review-text">
                                    {`"Icing sweet roll gingerbread sweet roll cupcake tart..."`}
                                </p>
                                <p className="reviewer-name">{`Reviewer ${index}`}</p>
                            </div>
                        ))}
                    </div>
                </section>
            </main>
        </div>
    );
}
