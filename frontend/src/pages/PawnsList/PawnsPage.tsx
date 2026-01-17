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
        console.log(data);
      } catch (error) {
        if (error.response && error.response.data) {
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
    <div className="min-h-screen bg-gradient-to-b from-slate-800 via-zinc-900 to-neutral-950 text-gray-200 p-6">
      <div className="max-w-7xl mx-auto">
        <div className="text-center mb-10">
          <h1 className="text-5xl font-serif text-amber-400 drop-shadow-lg mb-2">
            Pawn Guild
          </h1>
          <p className="text-gray-400 text-sm">Dragon's Dogma 2</p>
        </div>

        <div className="flex justify-center gap-4 mb-8 flex-wrap">
          {platforms.map((platform) => (
            <button
              key={platform}
              onClick={() => handlePlatformClick(platform)}
              className={`px-6 py-2 rounded-lg font-semibold text-sm tracking-wide transition-all shadow-lg border ${
                selectedPlatform === platform
                  ? "bg-gradient-to-r from-amber-600 to-amber-700 text-black border-amber-500"
                  : "bg-zinc-900/80 text-amber-300 border-amber-800/40 hover:bg-amber-600 hover:text-black"
              }`}
            >
              {platform}
            </button>
          ))}
        </div>

        <div className="overflow-x-auto rounded-lg border-2 border-amber-700/40 bg-gradient-to-br from-zinc-800/95 to-zinc-900/95 shadow-2xl backdrop-blur-sm">
          <table className="min-w-full text-sm">
            <thead className="bg-zinc-950/80 border-b border-amber-700/40">
              <tr>
                <th className="px-4 py-4 text-left font-serif text-amber-400 tracking-wide">
                  Friend Link
                </th>
                <th className="px-4 py-4 text-left font-serif text-amber-400 tracking-wide">
                  Vocation
                </th>
                <th className="px-4 py-4 text-left font-serif text-amber-400 tracking-wide">
                  Pawn Name
                </th>
                <th className="px-4 py-4 text-left font-serif text-amber-400 tracking-wide">
                  Level
                </th>
                <th className="px-4 py-4 text-left font-serif text-amber-400 tracking-wide">
                  Inclinations
                </th>
                <th className="px-4 py-4 text-left font-serif text-amber-400 tracking-wide">
                  Gender
                </th>
                <th className="px-4 py-4 text-center font-serif text-amber-400 tracking-wide">
                  Access
                </th>
              </tr>
            </thead>

            <tbody>
              {pawns.map((pawn, idx) => (
                <tr
                  key={idx}
                  className="odd:bg-zinc-900/40 even:bg-zinc-800/40 hover:bg-amber-900/20 transition-colors"
                >
                  <td className="px-4 py-3">{pawn.platformIdentifier}</td>
                  <td className="px-4 py-3">{pawn.vocations}</td>
                  <td className="px-4 py-3 font-semibold text-amber-300">
                    {pawn.name}
                  </td>
                  <td className="px-4 py-3">{pawn.level}</td>
                  <td className="px-4 py-3">{pawn.inclinations}</td>
                  <td className="px-4 py-3">{pawn.gender}</td>
                  <td className="px-4 py-3 text-center">
                    <button
                      onClick={() => handleViewPawnClick(pawn.pawnId)}
                      className="px-4 py-2 rounded bg-gradient-to-r from-amber-600 to-amber-700 text-black font-bold shadow-lg hover:from-amber-500 hover:to-amber-600 transition-all"
                    >
                      View Pawn
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {loading && (
          <p className="text-center mt-6 text-amber-400 font-medium">
            Loading pawns...
          </p>
        )}
      </div>
    </div>
  );
}
