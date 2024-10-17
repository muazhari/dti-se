import {
    Link,
    Navbar,
    NavbarBrand,
    NavbarContent,
    NavbarItem,
    NavbarMenu,
    NavbarMenuItem,
    NavbarMenuToggle
} from "@nextui-org/react";
import React from "react";
import {usePathname} from "next/navigation";

export default function Header() {
    const pathname = usePathname()
    const [isMenuOpen, setIsMenuOpen] = React.useState(false);
    const menuItems = [
        {name: "Home", href: "/"},
        {name: "About Us", href: "/about-us"},
        {name: "Team", href: "/team"},
        {name: "Pricing", href: "/pricing"},
    ]

    return (
        <header className="bg-gray-100 sticky top-0 z-50">
            <div className="container mx-auto">
                <Navbar onMenuOpenChange={setIsMenuOpen}>
                    <NavbarBrand>
                        <Link href="/" color="foreground" className="font-bold text-3xl">
                            Company Logo
                        </Link>
                    </NavbarBrand>
                    <NavbarContent className="hidden md:flex gap-4" justify="center">
                        {menuItems.map((item, index) => (
                            <NavbarItem key={index} isActive={pathname === item.href}>
                                <Link href={item.href} color="foreground">
                                    {item.name}
                                </Link>
                            </NavbarItem>
                        ))}
                    </NavbarContent>
                    <NavbarMenuToggle
                        aria-label={isMenuOpen ? "Close menu" : "Open menu"}
                        className="md:hidden"
                    />
                    <NavbarMenu className="self-end">
                        {menuItems.map((item, index) => (
                            <NavbarMenuItem key={`${item}-${index}`} isActive={pathname === item.href}>
                                <Link
                                    color="foreground"
                                    className="w-full"
                                    href={item.href}
                                    size="lg"
                                >
                                    {item.name}
                                </Link>
                            </NavbarMenuItem>
                        ))}
                    </NavbarMenu>
                </Navbar>
            </div>
        </header>
    );
}
