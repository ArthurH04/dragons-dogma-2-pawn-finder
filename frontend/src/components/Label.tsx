export default function Label({children, required}) {
    return (
        <label className="text-sm text-zinc-300">
            {children}
            {required && <span className="text-red-500"> *</span>}
        </label>
    )
}