def main():
    f = open("canzoni.txt", "r", encoding="utf-8")
    fw = open("out.txt", "w", encoding="utf-8")
    for item in f:
        i1 = item.replace("'", "''")
        fw.write(i1)  

    
if __name__ == "__main__":
    main()