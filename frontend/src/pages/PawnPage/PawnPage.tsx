import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getPawnById } from "../../services/pawnService";

interface Pawn {
  pawnId: string;
  platform: string;
  platformIdentifier: string;
  vocations: string;
  name: string;
  level: number;
  inclinations: string;
  specializations: string;
  notes: string;
  gender: string;
  imageUrl: string;
  createdBy: string;
}

const PLATFORM_STYLES = {
  XBOX: "from-green-700/80 to-green-900/80 border-green-400/40 text-green-100",
  PLAYSTATION:
    "from-blue-700/80 to-blue-900/80 border-blue-400/40 text-blue-100",
  STEAM: "from-zinc-700 to-zinc-900 border-zinc-400/40 text-zinc-200",
};

const getPlatformStyles = (platform: string) => {
  return (
    PLATFORM_STYLES[platform.toUpperCase() as keyof typeof PLATFORM_STYLES] ||
    "from-amber-700/70 to-amber-900/70 border-amber-500/40 text-amber-200"
  );
};

export default function PawnPage() {
  const { id } = useParams();
  const [pawn, setPawn] = useState<Pawn | null>(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchPawn = async () => {
      if (!id) return;
      try {
        setLoading(true);
        const data = await getPawnById(id);
        setPawn(data);
      } catch (error) {
        if (error.response && error.response.data) {
          console.log(error.response.data.message || "Failed to fetch pawn");
        }
      } finally {
        setLoading(false);
      }
    };
    fetchPawn();
  }, [id]);

  if (loading) return <p>Loading...</p>;
  if (!pawn) return <p>Pawn not found.</p>;

  return (
    <div
      className="min-h-screen flex items-center justify-center px-6 py-16
    bg-gradient-to-b from-neutral-900 via-zinc-950 to-black"
    >
      <div
        className="
      max-w-5xl w-full
      grid grid-cols-1 md:grid-cols-[280px_1fr]
      gap-8
      bg-gradient-to-br from-[#2a1f17] via-[#1e1813] to-[#14100d]
      border border-amber-700/40
      rounded-xl
      shadow-2xl
      backdrop-blur-sm
      p-8
    "
      >
        <div
          className="
        flex items-center justify-center
        bg-gradient-to-br from-zinc-800/60 to-zinc-900/80
        border border-amber-700/30
        rounded-lg
        aspect-square
      "
        >
          {pawn.imageUrl ? (
            <img
              src={pawn.imageUrl}
              alt={pawn.name}
              className="w-full h-full object-cover rounded-lg"
            />
          ) : (
            <div className="text-amber-700/40 text-7xl">👤</div>
          )}
        </div>

        <div className="flex flex-col justify-between">
          <div>
            <h1 className="text-4xl font-serif text-amber-400 mb-3">
              {pawn.name}
            </h1>

            <div className="flex flex-wrap gap-2 mb-6">
              <span
                className="
    px-4 py-1.5
    rounded-full
    bg-gradient-to-r from-blue-700/80 to-blue-900/80
    border border-blue-400/30
    text-[11px]
    font-serif
    uppercase
    tracking-widest
    text-blue-100
    shadow-md
  "
              >
                {pawn.vocations}
              </span>
              <span
                className={`
    px-4 py-1.5 rounded-full
    bg-gradient-to-r
    ${getPlatformStyles(pawn.platform)}
    text-[11px] font-serif uppercase tracking-widest
    shadow-md
  `}
              >
                {pawn.platform}
              </span>
              <span
                className="
    px-4 py-1.5
    rounded-full
    bg-gradient-to-r from-blue-500 to-blue-600
    border border-amber-600/40
    text-[11px]
    font-serif
    uppercase
    tracking-widest
    text-white
    shadow-md
  "
              >
                Level {pawn.level}
              </span>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-y-3 gap-x-12 text-sm text-amber-100">
              <p>
                <span className="text-amber-400">Pawn ID:</span> {pawn.pawnId}
              </p>
              <p>
                <span className="text-amber-400">Platform ID:</span>{" "}
                {pawn.platformIdentifier}
              </p>

              <p>
                <span className="text-amber-400">Gender:</span> {pawn.gender}
              </p>
              <p>
                <span className="text-amber-400">Inclination:</span>{" "}
                {pawn.inclinations}
              </p>

              <p>
                <span className="text-amber-400">Specialization:</span>{" "}
                {pawn.specializations}
              </p>
            </div>

            {pawn.notes && (
              <div className="mt-6 text-sm text-amber-100">
                <p className="text-amber-400 mb-1">Notes</p>
                <p className="max-h-24 overflow-auto pr-2 leading-relaxed">
                  {pawn.notes}
                </p>
              </div>
            )}
          </div>

          <div className="mt-8 pt-4 border-t border-amber-700/30 flex justify-between text-xs text-amber-300">
            <span>
              Created by{" "}
              <span className="text-amber-400">{pawn.createdBy}</span>
            </span>
            <span>{pawn.createdAt ?? "—"}</span>
          </div>
        </div>
      </div>
    </div>
  );
}
