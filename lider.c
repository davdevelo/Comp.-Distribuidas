#include "mpi.h"
#include <stdio.h>
#include <stdio.h> 
#include <time.h> 
#include <math.h> 
#include <stdlib.h>

int numGenerado;

int liderActual;
int num_mayor;
int num_recibe;
int num_actual;
int id_recibe;


int numRandomico()
{
    
    srand(MPI_Wtime()*1000000);
    numGenerado=(int)(((100+2.0)*rand())/(RAND_MAX+1.0));
    
   return numGenerado ;
}

int funcionLider (int id_act, int id_may)
{

    if (num_actual>num_recibe)
    {
        id_may=id_act;
        num_recibe=num_actual;
    }
       
    return id_may;
}
 
int main(int argc, char *argv[])
{
    
    int rank, size, recibe=0;
    
    int envio;
    
    MPI_Status estado;
 
    MPI_Init(&argc, &argv); 
    MPI_Comm_size(MPI_COMM_WORLD, &size); 
    MPI_Comm_rank(MPI_COMM_WORLD, &rank); 
 
    srand(time(NULL));     
    if(rank == 0)
    {

        liderActual=rank;
        num_actual=numRandomico();
        
        printf("\nSoy el proceso %d mi # generado es %d\n",rank,num_actual);

        envio=(liderActual*111)+num_actual;
        MPI_Send(&envio ,1 ,MPI_INT ,rank+1 ,0 ,MPI_COMM_WORLD);       
        
    }
    else
    {   
        
        MPI_Recv(&recibe ,1  ,MPI_INT ,rank-1  ,0 ,MPI_COMM_WORLD  ,&estado); 
        
        if (recibe<100)
        {
            id_recibe=0;
            num_recibe=recibe;    
        }
        else
        {
            id_recibe=recibe/111;
            num_recibe=recibe-(111*id_recibe);
        }    
        
        num_actual=numRandomico();

        printf("\nSoy el proceso %d , he recibido la ID= %d y el # mayor es %d y mi # generado es %d",rank,id_recibe,num_recibe,num_actual);
        
        liderActual=funcionLider (rank, id_recibe);      

        printf("\nEl lider actual es :%d\n",liderActual );    

        envio=(liderActual*111)+num_recibe;

        if(rank != size-1)
            MPI_Send(&envio, 1 ,MPI_INT ,rank+1 , 0 ,MPI_COMM_WORLD);
 
    }
 
    MPI_Finalize();
    return 0;
}