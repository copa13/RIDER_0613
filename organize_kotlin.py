import os
import re
import shutil

java_base = "/app/src/main/java"
os.makedirs(java_base, exist_ok=True)

# Find all .kt files across repo
for root, dirs, files in os.walk("/"):
    # Skip app/build, .git, etc.
    if "/app/build" in root or "/.git" in root or "/app/applet" in root:
        continue
    for file in files:
        if file.endswith(".kt"):
            src_path = os.path.join(root, file)
            # If already in app/src/main/java, skip
            if java_base in src_path:
                continue
            
            # Read package declaration
            package_name = None
            try:
                with open(src_path, "r", encoding="utf-8") as f:
                    for line in f:
                        match = re.match(r"^\s*package\s+([a-zA-Z0-9_.]+)", line)
                        if match:
                            package_name = match.group(1)
                            break
            except Exception as e:
                print(f"Error reading {src_path}: {e}")
                continue
            
            if package_name:
                pkg_dir = os.path.join(java_base, package_name.replace(".", "/"))
                os.makedirs(pkg_dir, exist_ok=True)
                dest_path = os.path.join(pkg_dir, file)
                shutil.copy2(src_path, dest_path)
                print(f"Moved {src_path} -> {dest_path}")
            else:
                print(f"No package found in {src_path}")
