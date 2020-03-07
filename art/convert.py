import subprocess

INKSCAPE_PATH = "inkscape"

INPUT_PATH = "icon.svg"

def conv(s):
	subprocess.call([
		INKSCAPE_PATH,
		"-w", str(s), "-h", str(s), "-f", "icon.svg", "-e", "scaled_" + str(s) + ".png"
	])

SCALES = [48, 72, 96, 144, 192]


for s in SCALES:
	conv(s)
