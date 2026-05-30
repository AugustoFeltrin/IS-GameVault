import NavBar from "../../components/NavBar";
import ProfileBanner from "../../components/ProfileBanner";
import Reviews from "../../components/Reviews";

export default function Profile() {
    
    return (
        <div className="flex flex-col min-h-screen">
            <header className="sticky top-0 z-50 h-16 w-full">
                <NavBar/>
            </header>
            
            <main className="flex-1 flex flex-col gap-10 w-full max-w-7xl mx-auto px-6 py-10">
                <div className="h-38 w-full">
                    <ProfileBanner/>
                </div>

                <div className="h-24 w-full flex-1">
                    <Reviews/>
                </div>
    

            </main>
        </div>
    );
}