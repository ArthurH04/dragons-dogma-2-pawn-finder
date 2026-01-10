import { useState } from "react";
import Dropzone, { useDropzone } from "react-dropzone";
import { uploadImage } from "../services/imageService";
import Label from "./Label";

export default function Modal({ isOpen, onClose, onSubmit, children }) {
  const [pawnId, setPawnId] = useState("");
  const [name, setName] = useState("");
  const [gender, setGender] = useState("");
  const [level, setLevel] = useState(1);
  const [vocations, setVocations] = useState("");
  const [inclinations, setInclinations] = useState("");
  const [specializations, setSpecializations] = useState("");
  const [notes, setNotes] = useState("");
  const [imageFile, setImagefile] = useState(null);
  const [platform, setPlatform] = useState("");
  const [platformIdentifier, setPlatformIdentifier] = useState("");
  const [loading, setLoading] = useState(false);

  if (!isOpen) return null;

  const VOCATIONS_DATA = [
    { id: 1, name: "FIGHTER" },
    { id: 2, name: "ARCHER" },
    { id: 3, name: "MAGE" },
    { id: 4, name: "THIEF" },
    { id: 5, name: "WARRIOR" },
    { id: 6, name: "SORCERER" },
  ];

  const INCLINATIONS_DATA = [
    { id: 1, name: "STRAIGHTFORWARD" },
    { id: 2, name: "KINDHEARTED" },
    { id: 3, name: "CALM" },
    { id: 4, name: "SIMPLE" },
  ];

  const SPECIALIZATIONS_DATA = [
    { id: 1, name: "APHONITE" },
    { id: 2, name: "CHIRURGEON" },
    { id: 3, name: "FORAGER" },
    { id: 4, name: "HAWKER" },
    { id: 5, name: "LOGISTICIAN" },
    { id: 6, name: "WOODLAND_WORDSMITH" },
  ];

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      setLoading(true);
      let uploadImageUrl = null;

      if (imageFile) {
        const uploadFormData = new FormData();
        uploadFormData.append("image", imageFile);
        const response = await uploadImage(uploadFormData);
        uploadImageUrl = response;
      }

      const pawnData = {
        pawnId: pawnId.toString(),
        name,
        gender,
        level,
        vocations,
        inclinations,
        specializations,
        notes,
        platform,
        platformIdentifier,
        imageUrl: uploadImageUrl,
      };

      await onSubmit(pawnData);
    } catch (error) {
      console.error("Error uploading image:", error);
    } finally {
      setLoading(false);
    }
  };
  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/70 backdrop-blur-sm">
      <div className="relative bg-zinc-900 text-gray-100 p-8 rounded-2xl shadow-2xl w-full max-w-5xl border border-zinc-700">
        <button
          onClick={onClose}
          className="absolute top-4 right-4 text-zinc-400 hover:text-yellow-400 text-2xl"
        >
          ×
        </button>

        <h2 className="text-3xl font-bold mb-6 text-center text-yellow-400 tracking-wide">
          Create Pawn
        </h2>

        <form onSubmit={handleSubmit} className="grid grid-cols-2 gap-4">
          <div>
            <Label required>Pawn ID</Label>
            <input
              type="text"
              value={pawnId}
              onChange={(e) => setPawnId(e.target.value)}
              className="w-full bg-zinc-800 border border-zinc-700 rounded p-2"
            />
          </div>

          <div>
            <Label required>Name</Label>
            <input
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
              className="w-full bg-zinc-800 border border-zinc-700 rounded p-2"
            />
          </div>

          <div>
            <Label required>Gender</Label>
            <select
              value={gender}
              onChange={(e) => setGender(e.target.value)}
              className="w-full bg-zinc-800 border border-zinc-700 rounded p-2"
            >
              <option value="" disabled>
                Select gender
              </option>
              <option value="MALE">MALE</option>
              <option value="FEMALE">FEMALE</option>
            </select>
          </div>

          <div>
            <Label required>Level</Label>
            <input
              type="number"
              value={level}
              min={1}
              max={999}
              onChange={(e) => {
                const value = Number(e.target.value);
                if (value >= 1 && value <= 999) setLevel(value);
              }}
              className="w-full bg-zinc-800 border border-zinc-700 rounded p-2"
            />
          </div>

          <div>
            <Label required>Vocation</Label>
            <select
              value={vocations}
              onChange={(e) => setVocations(e.target.value)}
              className="w-full bg-zinc-800 border border-zinc-700 rounded p-2"
            >
              <option value="" disabled>
                Select vocation
              </option>
              {VOCATIONS_DATA.map((v) => (
                <option key={v.id} value={v.name}>
                  {v.name}
                </option>
              ))}
            </select>
          </div>

          <div>
            <Label required>Inclination</Label>
            <select
              value={inclinations}
              onChange={(e) => setInclinations(e.target.value)}
              className="w-full bg-zinc-800 border border-zinc-700 rounded p-2"
            >
              <option value="" disabled>
                Select inclination
              </option>
              {INCLINATIONS_DATA.map((i) => (
                <option key={i.id} value={i.name}>
                  {i.name}
                </option>
              ))}
            </select>
          </div>

          <div>
            <Label required>Specialization</Label>
            <select
              value={specializations}
              onChange={(e) => setSpecializations(e.target.value)}
              className="w-full bg-zinc-800 border border-zinc-700 rounded p-2"
            >
              <option value="" disabled>
                Select specialization
              </option>
              {SPECIALIZATIONS_DATA.map((s) => (
                <option key={s.id} value={s.name}>
                  {s.name}
                </option>
              ))}
            </select>
          </div>

          <div>
            <Label required>Platform</Label>
            <select
              value={platform}
              onChange={(e) => setPlatform(e.target.value)}
              className="w-full bg-zinc-800 border border-zinc-700 rounded p-2"
            >
              <option value="" disabled>
                Select platform
              </option>
              <option value="PLAYSTATION">PLAYSTATION</option>
              <option value="XBOX">XBOX</option>
              <option value="STEAM">STEAM</option>
            </select>
          </div>

          <div>
            <Label required>Platform Identifier</Label>
            <input
              type="text"
              value={platformIdentifier}
              onChange={(e) => setPlatformIdentifier(e.target.value)}
              className="w-full bg-zinc-800 border border-zinc-700 rounded p-2"
            />
          </div>

          <div className="col-span-2">
            <Label required>Notes</Label>
            <textarea
              value={notes}
              onChange={(e) => setNotes(e.target.value)}
              rows={3}
              maxLength={255}
              className="w-full bg-zinc-800 border border-zinc-700 rounded p-2"
            />
          </div>

          <div className="col-span-2">
            <Dropzone
              accept={{ "image/*": [] }}
              maxFiles={1}
              onDrop={(files) => setImagefile(files[0])}
            >
              {({ getRootProps, getInputProps }) => (
                <div
                  {...getRootProps()}
                  className="border-2 border-dashed border-zinc-600 p-6 text-center rounded cursor-pointer hover:border-yellow-500 transition"
                >
                  <input {...getInputProps()} />
                  {imageFile ? (
                    <p className="text-yellow-400">{imageFile.name}</p>
                  ) : (
                    <p className="text-zinc-400">
                      Drag & drop pawn image or click
                    </p>
                  )}
                </div>
              )}
            </Dropzone>
          </div>

          <div className="col-span-2 flex justify-center mt-4">
            <button
              type="submit"
              disabled={loading}
              className="bg-yellow-500 text-black px-8 py-2 rounded-lg font-bold hover:bg-yellow-400 transition"
            >
              {loading ? "Summoning..." : "Summon Pawn"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
