#include <fstream>
#include <sstream>
#include <string>
#include <iostream>
using namespace std;
int main(){
    ifstream infile("SuppliersJSON.txt");
    string line;
    int i = 0;
    while(getline(infile, line)){
        i++;
        istringstream iss(line);
        string filename;
        stringstream ss;
        ss <<"documentos/"<< i << ".txt";
        ss >>filename;
        cout << filename << endl;
        ofstream outfile(filename.c_str());
        line.erase(0,1);
        line.erase(line.size()-1);
        outfile << line;
        outfile.close();
    }
}
