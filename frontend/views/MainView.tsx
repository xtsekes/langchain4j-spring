import {AppLayout} from "@hilla/react-components/AppLayout";
import {Link, NavLink, Outlet} from "react-router-dom";
import {Suspense} from "react";
import {DrawerToggle} from "@hilla/react-components/DrawerToggle";

const navLinkClasses = ({ isActive }: any) => {
    return `block rounded-m p-s ${isActive ? 'bg-primary-10 text-primary' : 'text-body'}`;
};

export default function MainView() {
    return (
        <AppLayout primarySection="drawer">
            <div slot="drawer" className="flex flex-col justify-between h-full p-m">
                <header className="flex flex-col gap-m">
                   <Link to={"/"}><h1 className="text-l m-0">LangChain4J/Spring Demo</h1></Link>
                    <nav>
                        <NavLink className={navLinkClasses} to="/chat">
                            Chat
                        </NavLink>
                        <NavLink className={navLinkClasses} to="/stream-chat">
                            Stream Chat
                        </NavLink>
                    </nav>
                </header>
            </div>

            <DrawerToggle slot="navbar" aria-label="Menu toggle"></DrawerToggle>

            <Suspense>
                <Outlet />
            </Suspense>
        </AppLayout>
    );
}
