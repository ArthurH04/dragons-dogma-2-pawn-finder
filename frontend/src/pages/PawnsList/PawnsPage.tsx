import { useEffect, useState } from "react";
import Navbar from "../../components/Navbar";
import { useSearchParams, useNavigate } from "react-router-dom";
import { getAllPawns } from "../../services/pawnService";


interface Pawn {
  pawnId: string;
  platformIdentifier: string;
  vocations: string;
  name: string;
  level: number;
  inclinations: string[];
  gender: string;
}

const platforms = ["PLAYSTATION", "XBOX", "STEAM"];

export default function PawnsPage() {
  const [searchParams, setSearchParams] = useSearchParams();
  const [pawns, setPawns] = useState<Pawn[]>([]);
  const [loading, setLoading] = useState(false);

  const selectedPlatform = searchParams.get("platform") || "";

  useEffect(() => {
    const fetchPawns = async () => {
      try {
        setLoading(true);
        const data = await getAllPawns({ platform: selectedPlatform });
        setPawns(data.content || []);
        console.log(data)
      } catch (error) {
        if(error.response && error.response.data){
            console.log(error.response.data.message || "Failed to fetch pawns");
        }
      } finally {
        setLoading(false);
      }
    };
    fetchPawns();
  }, [selectedPlatform]);

  const handlePlatformClick = (platform: string) => {
    setSearchParams({ platform });
  };
  
  const navigate = useNavigate();

  const handleViewPawnClick = (pawnId: string) => {
    navigate(`/pawn/${pawnId}`);
  };

  return (
  <div className="min-h-screen bg-zinc-900 text-yellow-200 font-sans">
    <div className="p-6 max-w-7xl mx-auto">
      <h1 className="text-4xl font-extrabold mb-6 text-center text-yellow-300 tracking-wider drop-shadow-md">
        Pawn Guild
      </h1>

      <div className="flex justify-center gap-4 mb-6 flex-wrap">
        {platforms.map((platform) => (
          <button
            key={platform}
            onClick={() => handlePlatformClick(platform)}
            className={`px-5 py-2 rounded-lg font-semibold text-sm shadow-md transition-all duration-200 ${
              selectedPlatform === platform
                ? "bg-yellow-400 text-zinc-900 shadow-lg"
                : "bg-zinc-800 text-yellow-300 hover:bg-yellow-500 hover:text-zinc-900"
            }`}
          >
            {platform}
          </button>
        ))}
      </div>
    </div>

    <div className="overflow-x-auto px-6 pb-12">
      <table className="min-w-full bg-zinc-800 border border-yellow-600 rounded-lg shadow-md">
        <thead className="bg-zinc-700 border-b border-yellow-500">
          <tr>
            <th className="px-4 py-3 text-left text-yellow-300">Friend Link</th>
            <th className="px-4 py-3 text-left text-yellow-300">Vocation</th>
            <th className="px-4 py-3 text-left text-yellow-300">Pawn Name</th>
            <th className="px-4 py-3 text-left text-yellow-300">Level</th>
            <th className="px-4 py-3 text-left text-yellow-300">Inclinations</th>
            <th className="px-4 py-3 text-left text-yellow-300">Gender</th>
            <th className="px-4 py-3 text-center text-yellow-300">Access</th>
          </tr>
        </thead>

        <tbody>
          {pawns.map((pawn, idx) => (
            <tr
              key={idx}
              className="even:bg-zinc-700 odd:bg-zinc-800 hover:bg-yellow-900/20 transition-colors"
            >
              <td className="px-4 py-2 text-yellow-100">{pawn.platformIdentifier}</td>
              <td className="px-4 py-2 text-yellow-100">{pawn.vocations}</td>
              <td className="px-4 py-2 text-yellow-100 font-semibold">{pawn.name}</td>
              <td className="px-4 py-2 text-yellow-100">{pawn.level}</td>
              <td className="px-4 py-2 text-yellow-100">{pawn.inclinations}</td>
              <td className="px-4 py-2 text-yellow-100">{pawn.gender}</td>
              <td className="px-4 py-2 text-center">
                <button
                  onClick={() => handleViewPawnClick(pawn.pawnId)}
                  className="px-3 py-1 rounded bg-yellow-400 text-zinc-900 font-semibold hover:bg-yellow-500 shadow-md transition-colors"
                >
                  View pawn page
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {loading && (
        <p className="text-center mt-4 text-yellow-300 font-medium">
          Loading...
        </p>
      )}
    </div>
  </div>
);
}
