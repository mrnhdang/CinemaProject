package com.spring.cinemaproject.Services;

import com.spring.cinemaproject.Models.Chairs;
import com.spring.cinemaproject.Models.Rooms;
import com.spring.cinemaproject.Repositories.ChairRepository;
import com.spring.cinemaproject.Repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.*;

@Service
@Transactional
public class ChairService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ChairRepository chairRepository;
    public void createContinueChair( String rowName, Integer column, Integer roomID){
        List<Chairs> chairsList = chairRepository.findChairsByRooms(roomID);
        Integer countRoomChair = 0;
        for (Chairs item: chairsList ) {
            if(item.getChairName().contains(rowName.toUpperCase(Locale.ROOT)) == true){
                countRoomChair ++;
            }
        }
        for(int i= 1;i<=column ; i++){
            Chairs chair = new Chairs();
            int temp = countRoomChair;
            temp +=i;
            Rooms validateRoomChair = roomRepository.findRoomsByID(roomID);
            String validateSeatName = rowName.toUpperCase(Locale.ROOT)+temp;
            chair.setChairName(validateSeatName);
            chair.setRooms(validateRoomChair);

            chairRepository.save(chair);
        }
    }
    public void deleteAllRoomChair(Integer roomID){
        List<Chairs> chairsList = chairRepository.findChairsByRooms(roomID);
        for(Chairs item : chairsList){
            chairRepository.deleteById(item.getChairID());
        }

    }
    public void setChairDefault(Rooms rooms){
        List<Chairs> chairsList = chairRepository.findChairsByRooms(rooms.getRoomID());
        for(Chairs item : chairsList){
            item.setStatus(1);
            chairRepository.save(item);
        }

    }
    public Set<String> findExistAlphabet(Integer id){
        Set<String> alphabetExist = new HashSet<>();
        String[] alphabet = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        List<Chairs> getChairs=chairRepository.findChairsByRooms(id);
        for(int i =0; i<alphabet.length ; i++){
            for( Chairs item : getChairs ){
                if(item.getChairName().contains(alphabet[i].toUpperCase(Locale.ROOT)) == true){
                    alphabetExist.add(alphabet[i]);
                }
            }
        }
        return alphabetExist;
    }
}
