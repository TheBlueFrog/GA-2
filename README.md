

# GA-2

goal is to get an Agent to plan his actions by conceptualizing what it
will do before it does it.  then execute the plan, with feedback and
when reality diverges from the plan re-plan.

the genome of the agent contains the capability to conceptualize
but not the plan itself.  the actual planning process done by
the agent is a learned behavior. 

what's in the genome

  sensory inputs 
  sensory inputs effect the agent 
  the agent affects
  
  simplest possible is an agent that 
    eats stuff in the environment to stay alive
    has to move as the stuff is eaten
    learns to optimize 
    
  plant
    initially randomly placed
    regenerates at a fixed rate which involves spreading
    (add erratic water input later)
        
  agent 
    requires food to get energy
    moving takes energy
    eating consumes plant
    can sense food at some distance
    
    what is needed to have the agent plan food consumption?
        concept of time
        concept that eating consumes plants
        concept that plants regrow
     

## Back to routing
   
    Brooks - various behaviors running all the time
        hungry
        pickup
        agent learns how to prioritize the behaviors
        
    Instinct for pickup, once picked instinct to be dropped
    
### Cheap Routing
Sum the attraction of 

        pickups
        drops
        food
          move that way
          
probably not very interesting
          