package com.fiap.hackathon.gamechange.InnerLayer.usercases;

import com.fiap.hackathon.gamechange.InnerLayer.adapters.gateways.IGameGateway;
import com.fiap.hackathon.gamechange.InnerLayer.adapters.gateways.IProposalGateway;
import com.fiap.hackathon.gamechange.InnerLayer.adapters.gateways.IUserGateway;
import com.fiap.hackathon.gamechange.InnerLayer.entites.Game;
import com.fiap.hackathon.gamechange.InnerLayer.entites.Proposal;
import com.fiap.hackathon.gamechange.InnerLayer.entites.User;
import com.fiap.hackathon.gamechange.InnerLayer.entites.enums.AvailabilityStatus;
import com.fiap.hackathon.gamechange.InnerLayer.entites.enums.ProposalStatus;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.GameDTO;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.GameDTOMapper;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.ProposalDTO;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.ProposalDTOMapper;
import com.fiap.hackathon.gamechange.OutLayer.infrastructure.gateway.GameGatewayAux;
import com.fiap.hackathon.gamechange.OutLayer.infrastructure.persistence.IGameRepository;
import com.fiap.hackathon.gamechange.OutLayer.infrastructure.persistence.IUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProposalUseCase {

    private final IProposalGateway proposalGateway;
    private final IGameGateway gameGateway; // Gateway para acessar informações de jogos
    private final IGameRepository gameRepository;
    private final IUserRepository userRepository;
    private final IUserGateway userGateway; // Gateway para acessar informações de usuários

    // Injeção de dependência para acessar o IProposalGateway
    public ProposalUseCase(IProposalGateway proposalGateway, IGameGateway gameGateway, IGameRepository gameRepository, IUserRepository userRepository, IUserGateway userGateway) {
        this.proposalGateway = proposalGateway;
        this.gameGateway = gameGateway;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.userGateway = userGateway;
    }

    // CRIA UMA PROPOSTA E RETORNA UM DTO: OK
    public ProposalDTO saveProposal(ProposalDTO proposalDTO){
        // Converte o DTO para entidade
        Proposal proposalEntity = ProposalDTOMapper.toEntity(proposalDTO);

        // Verificar se os IDs dos jogos são válidos
        if (!gameGateway.isGameExists(proposalEntity.getGameOfferedId()) || !gameGateway.isGameExists(proposalEntity.getGameRequestedId())) {
            throw new IllegalArgumentException("Um dos jogos não existe.");
        }

        // Verificar se os IDs dos usuários são válidos
        if (!userGateway.isUserExists(proposalEntity.getProposerId()) || !userGateway.isUserExists(proposalEntity.getRecipientId())) {
            throw new IllegalArgumentException("Um dos usuários não é válido.");
        }

        // Salvar a proposta
        Proposal savedProposal = proposalGateway.saveProposal(proposalEntity);

        // Atualiza o availabilityStatus do jogo para "EM_NEGOCIACAO"
        gameGateway.updateGameAvailabilityStatus(savedProposal.getGameOfferedId(), AvailabilityStatus.EM_NEGOCIACAO);
        //gameGateway.updateGameAvailabilityStatus(savedProposal.getGameRequestedId(), AvailabilityStatus.EM_NEGOCIACAO);

        /////// ///// ////// /////
        // Obter os nomes dos jogos utilizando findById
        String gameOfferedTitle = gameRepository.findById(savedProposal.getGameOfferedId())
                .map(Game::getTitle) // Coleta o título se o jogo existir
                .orElseThrow(() -> new IllegalArgumentException("O jogo oferecido não foi encontrado."));

        String gameRequestedTitle = gameRepository.findById(savedProposal.getGameRequestedId())
                .map(Game::getTitle) // Coleta o título se o jogo existir
                .orElseThrow(() -> new IllegalArgumentException("O jogo solicitado não foi encontrado."));

        // Obter os nomes dos usuários utilizando userRepository
        String proposerName = userRepository.findById(savedProposal.getProposerId())
                //.map(User::getName)
                .map(User::getLogin)
                .orElseThrow(() -> new IllegalArgumentException("O proponente não foi encontrado."));

        String recipientName = userRepository.findById(savedProposal.getRecipientId())
                //.map(User::getName)
                .map(User::getLogin)
                .orElseThrow(() -> new IllegalArgumentException("O destinatário não foi encontrado."));

        // Retornar o DTO com os nomes adicionais
        return ProposalDTOMapper.toDTO(
                savedProposal,
                gameOfferedTitle,
                gameRequestedTitle,
                proposerName,
                recipientName
        );
    }

    // BUSCA LISTA COM TODOS OS PROPOSALS: OK
    public List<ProposalDTO> getAllProposal() {
        List<Proposal> proposals = proposalGateway.getAllProposals(); // Pega a lista de propostas
        List<ProposalDTO> proposalDTOs = new ArrayList<>();

        for (Proposal proposal : proposals) {
            /////// ///// ////// /////
            // Obter os nomes dos jogos utilizando findById
            String gameOfferedTitle = gameRepository.findById(proposal.getGameOfferedId())
                    .map(Game::getTitle) // Coleta o título se o jogo existir
                    .orElseThrow(() -> new IllegalArgumentException("O jogo oferecido não foi encontrado."));

            String gameRequestedTitle = gameRepository.findById(proposal.getGameRequestedId())
                    .map(Game::getTitle) // Coleta o título se o jogo existir
                    .orElseThrow(() -> new IllegalArgumentException("O jogo solicitado não foi encontrado."));

            // Obter os nomes dos usuários utilizando userRepository
            String proposerName = userRepository.findById(proposal.getProposerId())
                    //.map(User::getName)
                    .map(User::getLogin)
                    .orElseThrow(() -> new IllegalArgumentException("O proponente não foi encontrado."));

            String recipientName = userRepository.findById(proposal.getRecipientId())
                    //.map(User::getName)
                    .map(User::getLogin)
                    .orElseThrow(() -> new IllegalArgumentException("O destinatário não foi encontrado."));

            // Cria um DTO usando os dados coletados
            ProposalDTO dto = ProposalDTOMapper.toDTO(
                    proposal,
                    gameOfferedTitle,
                    gameRequestedTitle,
                    proposerName,
                    recipientName
            );

            proposalDTOs.add(dto); // Adiciona o DTO à lista
        }

        return proposalDTOs; // Retorna a lista de ProposalDTO
    }

    //BUSCA UM PROPOSAL POR ID: OK
    public Optional<ProposalDTO> getProposalById(String id) {
        Optional<Proposal> proposalOpt = proposalGateway.getProposalById(id);

        return proposalOpt.map(proposal -> {
            String gameOfferedTitle = gameRepository.findById(proposal.getGameOfferedId())
                    .map(Game::getTitle) // Coleta o título se o jogo existir
                    .orElseThrow(() -> new IllegalArgumentException("O jogo oferecido não foi encontrado."));

            String gameRequestedTitle = gameRepository.findById(proposal.getGameRequestedId())
                    .map(Game::getTitle) // Coleta o título se o jogo existir
                    .orElseThrow(() -> new IllegalArgumentException("O jogo solicitado não foi encontrado."));

            // Obter os nomes dos usuários utilizando userRepository
            String proposerName = userRepository.findById(proposal.getProposerId())
                    //.map(User::getName)
                    .map(User::getLogin)
                    .orElseThrow(() -> new IllegalArgumentException("O proponente não foi encontrado."));

            String recipientName = userRepository.findById(proposal.getRecipientId())
                    //.map(User::getName)
                    .map(User::getLogin)
                    .orElseThrow(() -> new IllegalArgumentException("O destinatário não foi encontrado."));

            // Cria um DTO usando os dados coletados
            ProposalDTO dto = ProposalDTOMapper.toDTO(
                    proposal,
                    gameOfferedTitle,
                    gameRequestedTitle,
                    proposerName,
                    recipientName
            );
            //proposalDTOs.add(dto); // Adiciona o DTO à lista
            return dto;
            //return ProposalDTOMapper.toDTO(proposal, gameOfferedTitle, gameRequestedTitle, proposerName, recipientName);
        });

    }

    // DELETA UM PROPOSAL POR ID: OK
    public void deleteProposal(String id){
        proposalGateway.deleteProposal(id);
    }

    // USUARIO QUE RECEBE PROPOSTA ATUALIZA STATUS DA PROPOSAL: OK
//    public Optional<ProposalDTO> updateProposalStatus(String recipientId, String proposalId, ProposalStatus newStatus) {
//        Optional<ProposalDTO> proposalDTOOpt = getProposalById(proposalId);  // Reaproveita a lógica
//
//        if (proposalDTOOpt.isPresent()) {
//            ProposalDTO proposalDTO = proposalDTOOpt.get();
//
//            // Busca a entidade Proposal original para atualizá-la
//            Optional<Proposal> proposalOpt = proposalGateway.getProposalById(proposalId);
//
//            // Verifica se o recipientId corresponde ao da proposta
//            if (proposalDTO.getRecipientId().equals(recipientId)) {
//                // Cria uma nova instância de Proposal com o status atualizado
//                Proposal updatedProposal = proposalGateway.getProposalById(proposalId)
//                        .get().withStatus(newStatus);
//
//                proposalGateway.saveProposal(updatedProposal); // Salva a nova proposta com o status atualizado
//
//                // Reutiliza o ProposalDTO existente e retorna
//                return Optional.of(proposalDTO);
//            }
//        }
//        return Optional.empty();  // Se não encontrou a proposta ou recipientId não corresponde
//    }


    public Optional<ProposalDTO> updateProposalStatus(String recipientId, String proposalId, ProposalStatus newStatus) {
        Optional<ProposalDTO> proposalDTOOpt = getProposalById(proposalId);  // Reaproveita a lógica para buscar Proposal

        if (proposalDTOOpt.isPresent()) {
            ProposalDTO proposalDTO = proposalDTOOpt.get();

            // Verifica se o recipientId corresponde ao da proposta
            if (proposalDTO.getRecipientId().equals(recipientId)) {

                // Busca a entidade Proposal original para atualizá-la
                Optional<Proposal> proposalOpt = proposalGateway.getProposalById(proposalId);
                if (proposalOpt.isPresent()) {
                    Proposal proposal = proposalOpt.get();

                    // Cria uma nova instância de Proposal com o status atualizado
                    Proposal updatedProposal = proposal.withStatus(newStatus);  // Atualiza o status

                    // Salva a nova proposta com o status atualizado
                    proposalGateway.saveProposal(updatedProposal);

                    ///**********************************************************************///
                    // Atualiza o availabilityStatus com base no novo status da proposta
                    if (newStatus == ProposalStatus.ACEITA) {
                        gameGateway.updateGameAvailabilityStatus(updatedProposal.getGameOfferedId(), AvailabilityStatus.TROCADO);
                        gameGateway.updateGameAvailabilityStatus(updatedProposal.getGameRequestedId(), AvailabilityStatus.TROCADO);
                    } else if (newStatus == ProposalStatus.PENDENTE || newStatus == ProposalStatus.RECUSADA) {
                        gameGateway.updateGameAvailabilityStatus(updatedProposal.getGameOfferedId(), AvailabilityStatus.DISPONIVEL);
                        gameGateway.updateGameAvailabilityStatus(updatedProposal.getGameRequestedId(), AvailabilityStatus.DISPONIVEL);
                    } else if (newStatus == ProposalStatus.NEGOCIANDO) {
                        gameGateway.updateGameAvailabilityStatus(updatedProposal.getGameOfferedId(), AvailabilityStatus.EM_NEGOCIACAO);
                        gameGateway.updateGameAvailabilityStatus(updatedProposal.getGameRequestedId(), AvailabilityStatus.EM_NEGOCIACAO);
                    }
                    ///**********************************************************************///

                    // Agora, coleta as informações atualizadas dos jogos e usuários
                    String gameOfferedTitle = gameRepository.findById(proposal.getGameOfferedId())
                            .map(Game::getTitle) // Coleta o título se o jogo existir
                            .orElseThrow(() -> new IllegalArgumentException("O jogo oferecido não foi encontrado."));

                    String gameRequestedTitle = gameRepository.findById(proposal.getGameRequestedId())
                            .map(Game::getTitle) // Coleta o título se o jogo existir
                            .orElseThrow(() -> new IllegalArgumentException("O jogo solicitado não foi encontrado."));

                    // Obter os nomes dos usuários utilizando userRepository
                    String proposerName = userRepository.findById(proposal.getProposerId())
                            //.map(User::getName)
                            .map(User::getLogin)
                            .orElseThrow(() -> new IllegalArgumentException("O proponente não foi encontrado."));

                    String recipientName = userRepository.findById(proposal.getRecipientId())
                            //.map(User::getName)
                            .map(User::getLogin)
                            .orElseThrow(() -> new IllegalArgumentException("O destinatário não foi encontrado."));

                    // Retorna o ProposalDTO atualizado com o novo status
                    return Optional.of(ProposalDTOMapper.toDTO(
                            updatedProposal, gameOfferedTitle, gameRequestedTitle, proposerName, recipientName
                    ));
                }
            }
        }

        return Optional.empty();  // Retorna vazio se não encontrou a proposta ou recipientId não corresponde
    }

}
