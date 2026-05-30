import NavBar from "../../components/NavBar";
import Catalog from "../../components/Catalog";
import FindYourGame from "../../components/FindYourGame";

export default function Home() {
    return (
        <div className="flex flex-col min-h-screen">
            <header className="sticky top-0 z-50 h-16 w-full">
                <NavBar />
            </header>

            <main className="flex-1 flex flex-col gap-10 w-full max-w-7xl mx-auto px-6 py-10">
                <div className="w-full h-24">
                    <FindYourGame/>
                </div>

                <div className="w-full h-24 flex-1">
                    <Catalog />
                </div>
            </main>
        </div>
    );
}