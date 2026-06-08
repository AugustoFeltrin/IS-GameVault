export default function ProfileBanner() {
    const userName = localStorage.getItem("gamevault_user_name") || "Jogador";
    const userEmail = localStorage.getItem("gamevault_user_email") || "email@indisponivel.com";

    const userInitials = userName.substring(0, 2).toUpperCase();

    return (
        <div className="bg-surface border border-gray-500/20 px-8 h-full w-full rounded-3xl flex justify-between">
            <div className="flex gap-4 justify-center items-center">
                <div className="bg-primary border-black border-3 h-22 w-22 rounded-full flex justify-center items-center">
                    <span className="text-2xl font-bold">{userInitials}</span>
                </div>

                <div className="flex flex-col gap-2">
                    <span className="text-2xl font-bold">{userName}</span>
                    <span className="text-sm text-gray-400">{userEmail}</span>
                    <span className="text-sm text-gray-400">Último acesso há 2h</span>
                </div>
            </div>

            <div className="flex gap-6 items-center justify-center">
                <div className="bg-background/40 border border-gray-500/70 h-20 w-28 px-4 rounded-3xl flex flex-col justify-center">
                    <span className="text-sm text-gray-400">Jogados</span>
                    <span className="text-lg font-bold">5</span>
                </div>

                <div className="bg-background/40 border border-gray-500/70 h-20 w-28 px-4 rounded-3xl flex flex-col justify-center">
                    <span className="text-sm text-gray-400">Nota Média</span>                   
                    <span className="text-lg font-bold">4.2</span>                   
                </div>

                <div className="bg-background/40 border border-gray-500/70 h-20 w-28 px-4 rounded-3xl flex flex-col justify-center">
                    <span className="text-sm text-gray-400">Favoritos</span>               
                    <span className="text-lg font-bold">3</span>               
                </div>
            </div>
        </div>
    )
}